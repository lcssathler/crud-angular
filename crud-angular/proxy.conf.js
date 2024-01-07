const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://3.17.76.127:8080/',
    secure: 'false',
    logLevel: 'debug'
  }
];

module.exports = PROXY_CONFIG;
