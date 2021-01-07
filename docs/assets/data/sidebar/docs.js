sidebar = {
  menus: [{
    id: 'index',
    icon: 'dashboard',
    name: 'OverView',
  }, {
    id: 'quick-start',
    icon: 'dashboard',
    name: 'Quick Start',
    menus: [{
      id: 'start-entity',
      name: 'Start Entity',
      menus: [{
        id: 'define-entity',
        name: 'Define Entity',
      }]
    }, {
      id: 'start-mybatis',
      name: 'Start Mybatis',
      menus: [{
        id: 'define-mapper',
        name: 'Define Mapper'
      }, {
        id: 'define-query',
        name: 'Define Query'
      }]
    }, {
      id: 'start-with-service',
      name: 'Start Service',
    }, {
      id: 'start-web',
      name: 'Start Web',
      menus: [{
        id: 'define-rest-api',
        name: 'Define RestApi',
      }]
    }]
  }, {
    id: 'features',
    icon: 'dashboard',
    name: 'Features',
    menus: []
  }]
}