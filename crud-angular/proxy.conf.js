const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: 'http://crud-course-deploy-render.onrender.com/',
    // target: 'http://localhost:8080/',
    secure: 'false',
    logLevel: 'debug'
  }
];

module.exports = PROXY_CONFIG;
