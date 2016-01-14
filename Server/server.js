var express         = require('express');
var path            = require('path'); // модуль для парсинга пути
var log             = require('./libs/log')(module);
var config          = require('./libs/config');
var ArticleModel    = require('./libs/mongoose').ArticleModel;
var app = express();

app.use(express.favicon()); // отдаем стандартную фавиконку, можем здесь же свою задать
app.use(express.logger('dev')); // выводим все запросы со статусами в консоль
app.use(express.bodyParser()); // стандартный модуль, для парсинга JSON в запросах
app.use(express.methodOverride()); // поддержка put и delete
app.use(app.router); // модуль для простого задания обработчиков путей
app.use(express.static(path.join(__dirname, "public"))); // запуск статического файлового сервера, который смотрит на папку public/ (в нашем случае отдает index.html)

app.get('/api', function (req, res) {
    res.send('API is running');
});

app.listen(config.get('port'), function(){
    log.info('Express server listening on port ' + config.get('port'));
});

app.use(function(req, res, next){
    res.status(404);
    log.debug('Not found URL: %s',req.url);
    res.send({ error: 'Not found' });
    return;
});

app.use(function(err, req, res, next){
    res.status(err.status || 500);
    log.error('Internal error(%d): %s',res.statusCode,err.message);
    res.send({ error: err.message });
    return;
});

app.get('/ErrorExample', function(req, res, next){
    next(new Error('Random error!'));
});

// GET /devices
app.get('/devices', function(req, res) {
    return DeviceModel.find(function (err, devices) {
        if (!err) {
            return res.send(devices);
        } else {
            res.statusCode = 500;
            log.error('Internal error(%d): %s',res.statusCode,err.message);
            return res.send({ error: 'Server error' });
        }
    });
});

// POST /devices
app.post('/devices', function(req, res) {
    DeviceModel.findOne({ 'name': req.body.name }, function (err, device) {
        if(!device) {
			var newDevice = new DeviceModel({
				name: req.body.name,
				weatherData: req.body.weatherData
			});
			newDevice.save(function (err) {
				if (!err) {
					log.info("new device created");
					return res.send({ status: 'OK', newDevice:newDevice });
				} else {
					console.log(err);
					if(err.name == 'ValidationError') {
						res.statusCode = 400;
						res.send({ error: 'Validation error' });
					} else {
						res.statusCode = 500;
						res.send({ error: 'Server error' });
					}
					log.error('Internal error(%d): %s',res.statusCode,err.message);
				}
			});
        } else {
			device.weatherData: req.body.weatherData
			return device.save(function (err) {
				if (!err) {
					log.info("device updated");
					return res.send({ status: 'OK', device:device });
				} else {
					if(err.name == 'ValidationError') {
						res.statusCode = 400;
						res.send({ error: 'Validation error' });
					} else {
						res.statusCode = 500;
						res.send({ error: 'Server error' });
					}
					log.error('Internal error(%d): %s',res.statusCode,err.message);
				}
			});
		}
    });
});

// GET /device/:name
app.get('/device/:name', function(req, res) {
    return DeviceModel.findById(req.params.id, function (err, device) {
        if(!device) {
            res.statusCode = 404;
            return res.send({ error: 'Not found' });
        }
        if (!err) {
            return res.send({ status: 'OK', article:article });
        } else {
            res.statusCode = 500;
            log.error('Internal error(%d): %s',res.statusCode,err.message);
            return res.send({ error: 'Server error' });
        }
    });
});

app.put('/api/articles/:id', function (req, res){
    return ArticleModel.findById(req.params.id, function (err, article) {
        if(!article) {
            res.statusCode = 404;
            return res.send({ error: 'Not found' });
        }

        article.title = req.body.title;
        article.description = req.body.description;
        article.author = req.body.author;
        article.images = req.body.images;
        return article.save(function (err) {
            if (!err) {
                log.info("article updated");
                return res.send({ status: 'OK', article:article });
            } else {
                if(err.name == 'ValidationError') {
                    res.statusCode = 400;
                    res.send({ error: 'Validation error' });
                } else {
                    res.statusCode = 500;
                    res.send({ error: 'Server error' });
                }
                log.error('Internal error(%d): %s',res.statusCode,err.message);
            }
        });
    });
});

