sidebar = {
  menus: [
    {
      id: 'index',
      icon: 'dashboard',
      name: 'OverView',
      url: '/docs',
    },
    {
      id: 'quick-start',
      icon: 'dashboard',
      name: 'Quick Start',
      url: '/docs/quick-start',
      menus: [{
        id: 'define-entity-with-annotation',
        name: 'Define Entity',
        url: '/docs/quick-start/define-entity-with-annotation'
      }, {
        id: 'start-with-mybatis',
        name: 'Start Mybatis',
        url: '/docs/quick-start/start-with-mybatis'
      }, {
        id: 'define-query-with-annotation',
        name: 'Define Query',
        url: '/docs/quick-start/define-query-with-annotation'
      }, {
        id: 'start-with-service',
        name: 'Start Service',
        url: '/docs/quick-start/start-with-service'
      }, {
        id: 'start-web',
        name: 'Start Web',
        url: '/docs/quick-start/start-web',
        menus: [{
          id: 'define-rest-api',
          name: 'Define RestApi',
          url: '/docs/quick-start/start-web/define-rest-api',
        }]
      }]
    }
  ]
}