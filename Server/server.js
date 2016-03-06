var express         = require('express');
var path            = require('path');
var log             = require('./libs/log')(module);
var config          = require('./libs/config');
var DeviceModel    = require('./libs/mongoose').DeviceModel;
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json()); // support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // support encoded bodies

app.use(function (req, res, next) {
  console.log(req.body) // populated!
  next()
})

app.use(express.static(path.join(__dirname, "public")));

app.get('/api', function (req, res) {
    res.send('API is running');
});

app.listen(config.get('port'), function(){
    log.info('Express server listening on port ' + config.get('port'));
});

app.get('/ErrorExample', function(req, res, next){
    next(new Error('Random error!'));
});

// GET /devices
app.get('/devices', function(req, res) {
    return DeviceModel.find().select( '-weatherData' ).exec(function (err, devices) {
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
			 device.weatherData.push(req.body.weatherData);
			 device.save(function (err) {
					if (!err) {
						log.info("device updated");
						return res.send({ status: 'OK' });
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

// GET /devices/:name/current
app.get('/devices/:name/current', function(req, res) {
	return DeviceModel.findOne({ 'name': req.params.name }, function (err, device) {
		if(!device) {
            res.statusCode = 404;
            return res.send({ error: 'Not found' });
        }
        if (!err) {
			var weather = device.weatherData.sort(function(a,b){
				  // Turn your strings into dates, and then subtract them
				  // to get a value that is either negative, positive, or zero.
				  return new Date(b.modified) - new Date(a.modified);
			})[0];
            return res.send(weather);
        } else {
            res.statusCode = 500;
            log.error('Internal error(%d): %s',res.statusCode,err.message);
            return res.send({ error: 'Server error' });
        }
	});
});

// GET /devices/:name/history
app.get('/devices/:name/history', function(req, res) {
	return DeviceModel.findOne({ 'name': req.params.name }, function (err, device) {
		if(!device) {
            res.statusCode = 404;
            return res.send({ error: 'Not found' });
        }
        if (!err) {
			var weather = device.weatherData.sort(function(a,b){
				  // Turn your strings into dates, and then subtract them
				  // to get a value that is either negative, positive, or zero.
				  return new Date(b.modified) - new Date(a.modified);
			})
			.slice(0,10000);
            return res.send(weather);
        } else {
            res.statusCode = 500;
            log.error('Internal error(%d): %s',res.statusCode,err.message);
            return res.send({ error: 'Server error' });
        }
	});
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