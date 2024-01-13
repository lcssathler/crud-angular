const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://crud-course-deploy-render.onrender.com/',
    secure: 'false',
    logLevel: 'debug'
  }
];

module.exports = PROXY_CONFIG;
