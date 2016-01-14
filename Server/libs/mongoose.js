var config      = require('./config');
var mongoose    = require('mongoose');
var log         = require('./log')(module);

mongoose.connect(config.get('mongoose:uri'));
var db = mongoose.connection;

db.on('error', function (err) {
    log.error('connection error:', err.message);
});
db.once('open', function callback () {
    log.info("Connected to DB!");
});

var Schema = mongoose.Schema;

// Schemas
var WeatherData = new Schema({
    temperature: { type: String, required: true },
	humidity: { type: String, required: true},
	modified: { type: Date, default: Date.now }
});

var Device = new Schema({
    name: { type: String, required: true, unique: true },
    weatherData: [WeatherData],
});

// validation
Device.path('name').validate(function (v) {
    return v.length > 0;
});

var DeviceModel = mongoose.model('Device', Device);

module.exports.DeviceModel = DeviceModel;