let Tables = new Vue({
    el: '#dashboard',
    data: {
        modules: [
            {
                id: 'documents',
                link: '/documents',
                logo: '/assets/img/modules/json.png',
                name: 'Final Framework',
                subtitle: 'Final Framework',
                description: '站在巨人的肩膀上，视野更宽广。'
            }
        ],
        sidebar: {
            logo: '/assets/img/modules/json.png',
            name: 'RequestMapping',
            menus: [
                {
                    id: 'requestMapping',
                    name: 'RequestMappings',
                    icon: 'table_chart',
                    link: '/dashboard/request/mappings'
                }
            ],

        },
        page: {
            menus: [],
            module: 'requestMapping'
        },
        filter: '',
        host: 'http://localhost:8080',
        tables: []
    },
    mounted: function () {
        let _this = this;
        let pageMenus = [];
        pageMenus.push($.query.string('logicTable'));
        pageMenus.push($.query.string('actualTable'));
        _this.page.menus = pageMenus;
        this.loadTables();
    },
    methods: {
        loadTables: function () {
            let _this = this;
            $.http.get({
                url: _this.host + '/api/jdbc/tables',
                data: {
                    schema: 'test'
                },
                callback: function (result) {
                    if (result.success) {
                        _this.tables = result.data;
                    }
                }
            })
        }
    }
});