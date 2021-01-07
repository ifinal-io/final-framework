sidebar = {
  menus: [{
    id: 'overview',
    icon: 'dashboard',
    name: 'OverView',
  }, {
    id: 'quick-start',
    icon: 'dashboard',
    name: 'Quick Start',
    menus: [{
      id: 'start-project',
      name: 'Start Project'
    }, {
      id: 'start-entity',
      name: 'Start Entity',
      menus: [{
        id: 'define-entity',
        name: 'Define Entity',
      }, {
        id: 'define-enum',
        name: 'Define Enum'
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
      id: 'start-service',
      name: 'Start Service',
    }, {
      id: 'start-web',
      name: 'Start Web',
      menus: [{
        id: 'define-rest-api',
        name: 'Define RestApi',
      }, {
        id: 'receive-json-param',
        name: 'Receive Json Param'
      }]
    }]
  }, {
    id: 'features',
    icon: 'dashboard',
    name: 'Features',
    menus: []
  }]
}