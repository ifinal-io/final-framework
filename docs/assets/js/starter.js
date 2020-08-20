/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

//格式化代码函数,已经用原生方式写好了不需要改动,直接引用就好
String.prototype.removeLineEnd = function () {
    return this.replace(/(<.+?\s+?)(?:\n\s*?(.+?=".*?"))/g, '$1 $2')
}
var starter = new Vue({
        el: '#starter',
        data: {
            final: {
                group: 'org.finalframework',
                artifact: 'final-framework',
                version: '1.0.0-SNAPSHOT',
                pom: ''
            },
            project: {
                type: 'maven',
                language: 'java',
                finalBootVersion: '1.0.0-SNAPSHOT',
                group: 'org.finalframework.starter',
                artifact: 'final-starter',
                name: "demo",
                description: '',
                packageName: '',
                packaging: 'pom',
                version: '1.0.0-SNAPSHOT',
                pom: '',
                javaVersion: '1.8'
            },
            modules: {
                entity: {
                    enable: true,
                    artifact: 'entity',
                    dependencies: {
                        finalDataStarter: {
                            enable: true,
                            group: 'org.finalframework.boot',
                            artifact: 'final-data-spring-boot-stater',
                            scope: 'compile'
                        }
                    }
                },
                dao: {
                    enable: true,
                    name: 'dao',
                    artifact: 'dao',
                    dependencies: []
                },
                service: {
                    enable: true,
                    name: 'entity',
                    artifact: 'entity',
                },
                web: {
                    enable: true,
                    name: 'entity',
                    artifact: 'entity',
                },
                man: {
                    enable: false,
                    name: 'entity',
                    artifact: 'entity',
                },
            },
            dependenciesGroup: {
                developerTools: {
                    name: 'Developer Tools',
                    dependencies: {
                        springBoolDevTools: {
                            name: 'Spring Boot DevTools',
                            checked: false
                        },
                        lomBok: {
                            name: 'Lombok',
                            checked: true
                        },
                        springConfigurationProcessor: {
                            name: 'Spring Configuration Processor',
                            checked: false
                        }
                    }
                },
                web: {
                    name: 'Web',
                    dependencies: {
                        springWeb: {
                            name: 'Spring Web',
                            checked: false
                        }
                    }
                }

            }


        },
        watch: {
            'project.artifact': function (value) {
                let _this = this;
                _this.modules.entity.artifact = value + '-entity';
                _this.modules.dao.artifact = value + '-dao';
                _this.modules.service.artifact = value + '-service';
                _this.modules.web.artifact = value + '-web';
                _this.modules.man.artifact = value + '-man';
            }
        },
        mounted: function () {
            let _this = this;
            let value = _this.project.artifact;
            _this.modules.entity.artifact = value + '-entity';
            _this.modules.dao.artifact = value + '-dao';
            _this.modules.service.artifact = value + '-service';
            _this.modules.web.artifact = value + '-web';
            _this.modules.man.artifact = value + '-man';

// Wizard Initialization
            $('.card-wizard').bootstrapWizard({
                'tabClass': 'starter nav nav-pills',
                'nextSelector': '.btn-next',
                'previousSelector': '.btn-previous',

                onNext: function (tab, navigation, index) {
                    // var $valid = $('.card-wizard form').valid();
                    // if (!$valid) {
                    //     $validator.focusInvalid();
                    //     return false;
                    // }
                },

                onInit: function (tab, navigation, index) {
                    //check number of tabs and fill the entire row
                    var $total = navigation.find('li').length;
                    var $wizard = navigation.closest('.card-wizard');

                    $first_li = navigation.find('li:first-child a').html();
                    $moving_div = $('<div class="moving-tab">' + $first_li + '</div>');
                    $('.card-wizard .wizard-navigation').append($moving_div);

                    _this.refreshAnimation($wizard, index);

                    $('.moving-tab').css('transition', 'transform 0s');
                },

                onTabClick: function (tab, navigation, index) {
                    var $valid = $('.card-wizard form').valid();

                    if (!$valid) {
                        return false;
                    } else {
                        return true;
                    }
                },

                onTabShow: function (tab, navigation, index) {
                    var $total = navigation.find('li').length;
                    var $current = index + 1;

                    var $wizard = navigation.closest('.card-wizard');

                    // If it's the last tab then hide the last button and show the finish instead
                    if ($current >= $total) {
                        $($wizard).find('.btn-next').hide();
                        $($wizard).find('.btn-finish').show();
                    } else {
                        $($wizard).find('.btn-next').show();
                        $($wizard).find('.btn-finish').hide();
                    }

                    button_text = navigation.find('li:nth-child(' + $current + ') a').html();

                    setTimeout(function () {
                        $('.moving-tab').text(button_text);
                    }, 150);

                    var checkbox = $('.footer-checkbox');

                    if (!index == 0) {
                        $(checkbox).css({
                            'opacity': '0',
                            'visibility': 'hidden',
                            'position': 'absolute'
                        });
                    } else {
                        $(checkbox).css({
                            'opacity': '1',
                            'visibility': 'visible'
                        });
                    }

                    _this.refreshAnimation($wizard, index);
                },

            });
            // alert(_this.root.pom);
        },
        methods: {
            refreshAnimation: function ($wizard, index) {
                $total = $wizard.find('.nav li').length;
                $li_width = 100 / $total;

                total_steps = $wizard.find('.nav li').length;
                move_distance = $wizard.width() / total_steps;
                index_temp = index;
                vertical_level = 0;

                mobile_device = $(document).width() < 600 && $total > 3;

                if (mobile_device) {
                    move_distance = $wizard.width() / 2;
                    index_temp = index % 2;
                    $li_width = 50;
                }

                $wizard.find('.nav li').css('width', $li_width + '%');

                step_width = move_distance;
                move_distance = move_distance * index_temp;

                $current = index + 1;

                if ($current == 1 || (mobile_device == true && (index % 2 == 0))) {
                    move_distance -= 8;
                } else if ($current == total_steps || (mobile_device == true && (index % 2 == 1))) {
                    move_distance += 8;
                }

                if (mobile_device) {
                    vertical_level = parseInt(index / 2);
                    vertical_level = vertical_level * 38;
                }

                $wizard.find('.moving-tab').css('width', step_width);
                $('.moving-tab').css({
                    'transform': 'translate3d(' + move_distance + 'px, ' + vertical_level + 'px, 0)',
                    'transition': 'all 0.5s cubic-bezier(0.29, 1.42, 0.79, 1)'

                });
            },
            onDependencyClick(dependency) {
                dependency.checked = !dependency.checked;
                // alert(dependency.checked);
            },
            formatXml: function (xml, tab) {
                var formatted = '', indent = '';
                tab = tab || '    ';
                xml.split(/>\s*</).forEach(function (node) {
                    if (node.match(/^\/\w/)) indent = indent.substring(tab.length);
                    formatted += indent + '<' + node + '>\r\n';
                    if (node.match(/^<?\w[^>]*[^\/]$/)) indent += tab;
                });
                return formatted.substring(1, formatted.length - 3);
            },
            escapeXml: function (unsafe) {
                unsafe = unsafe.replace(/ xmlns=""/g, '');
                unsafe = unsafe.replace(/ xmlns="http:\/\/www.w3.org\/1999\/xhtml"/g, '');
                return unsafe.replace(/[<>&'"]/g, function (c) {
                    switch (c) {
                        case '<':
                            return '&lt;';
                        case '>':
                            return '&gt;';
                        case '&':
                            return '&amp;';
                        case '\'':
                            return '&apos;';
                        case '"':
                            return '&quot;';
                    }
                });
            },
            rootPom: function () {

                let _this = this;

                var pom = `<?xml version="1.0" encoding="UTF-8"?>
                               <project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                        xmlns="http://maven.apache.org/POM/4.0.0"
                                        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                                   <parent>
                                       <artifactId>${_this.final.artifact}</artifactId>
                                       <groupId>${_this.final.group}</groupId>
                                       <version>${_this.project.finalBootVersion}</version>
                                   </parent>
                                   <modelVersion>4.0.0</modelVersion>
                                   <groupId>org.finalframework.test</groupId>
                                   <artifactId>${_this.project.artifact}</artifactId>
                                   <version>${_this.project.version}</version>
                                   <name>${_this.project.name}</name>
                                   <description>${_this.project.description}</description>
                                   <packaging>pom</packaging>
                                   <modules>
                                   </modules>

                                   <properties>
                                        <java.version>${_this.project.javaVersion}</java.version>
                                        <final.version>${_this.final.version}</final.version>
                                        <${_this.project.name}.version>${_this.project.version}</${_this.project.name}.version>
                                   </properties>
                                   
                                   <dependencies>
                                        ${_this.dependenciesGroup.developerTools.dependencies.lomBok.checked ? `<dependency>
                                            <groupId>org.projectlombok</groupId>
                                            <artifactId>lombok</artifactId>
                                            <optional>true</optional>
                                        </dependency>` : ''}
                                   </dependencies>

                                   <dependencyManagement>
                                        <dependencies>
                                            ${_this.modules.entity.enable ? `<dependency>
                                                <groupId>${_this.project.group}</groupId>
                                                <artifactId>${_this.project.artifact}-entity</artifactId>
                                                <version>\${${_this.project.name}.version}</version>
                                            </dependency>` : ''}
                                            ${_this.modules.dao.enable ? `<dependency>
                                                <groupId>${_this.project.group}</groupId>
                                                <artifactId>${_this.project.artifact}-dao</artifactId>
                                                <version>\${${_this.project.name}.version}</version>
                                            </dependency>` : ''}
                                            ${_this.modules.service.enable ? `<dependency>
                                                <groupId>${_this.project.group}</groupId>
                                                <artifactId>${_this.project.artifact}-service</artifactId>
                                                <version>\${${_this.project.name}.version}</version>
                                            </dependency>` : ''}
                                            ${_this.modules.web.enable ? `<dependency>
                                                <groupId>${_this.project.group}</groupId>
                                                <artifactId>${_this.project.artifact}-web</artifactId>
                                                <version>\${${_this.project.name}.version}</version>
                                            </dependency>` : ''}
                                            ${_this.modules.man.enable ? `<dependency>
                                                <groupId>${_this.project.group}</groupId>
                                                <artifactId>${_this.project.artifact}-man</artifactId>
                                                <version>\${${_this.project.name}.version}</version>
                                            </dependency>` : ''}
                                        </dependencies>
                                    </dependencyManagement>

                               </project>`,
                    xmlDoc = $.parseXML(pom),
                    $pom = $(xmlDoc);


                let $modules = $pom.find("modules");

                for (let index in _this.modules) {
                    let module = _this.modules[index];
                    if (module.enable) {
                        $modules.append(`<module>${module.artifact}</module>`);
                    }
                }


                return _this.formatXml((new XMLSerializer()).serializeToString(xmlDoc));
            },
            modulePom: function (module, dependencyModules) {
                let _this = this;

                var pom = `<?xml version="1.0" encoding="UTF-8"?>
                               <project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                                        xmlns="http://maven.apache.org/POM/4.0.0"
                                        xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                                   <parent>
                                        <groupId>${_this.project.group}</groupId>
                                        <artifactId>${_this.project.artifact}</artifactId>
                                        <version>${_this.project.version}</version>
                                   </parent>
                                   <modelVersion>4.0.0</modelVersion>
                                   <artifactId>${module.artifact}</artifactId>
                                   <packaging>jar</packaging>
                                   
                                   <dependencies>
                                        
                                   </dependencies>
                               </project>`,

                    xmlDoc = $.parseXML(pom),
                    $pom = $(xmlDoc);


                let $project = $pom.find("project");
                let $dependencies = $pom.find("dependencies");

                for (let index in dependencyModules) {
                    let dependency = dependencyModules[index];
                    let $dependency = $(`<dependency>
                                            <groupId>${_this.project.group}</groupId>
                                            <artifactId>${dependency.artifact}</artifactId>
                                        </dependency>`);
                    $dependencies.append($dependency);
                }


                for (let index in module.dependencies) {
                    let dependency = module.dependencies[index];
                    if (dependency.enable) {
                        let $dependency = $(`<dependency>
                                            <groupId>${dependency.group}</groupId>
                                            <artifactId>${dependency.artifact}</artifactId>
                                        </dependency>`);

                        if (dependency.scope != null) {
                            $dependency.append(`<scope>${dependency.scope}</scope>`)
                        }

                        if (dependency.optional != null) {
                            $dependency.append(`<optional>${dependency.optional}</optional>`)
                        }

                        $dependencies.append($dependency);

                    }

                }

                if (true) {
                    $project.append(`<build>
                                    <plugins>
                                      <plugin>
                                        <groupId>org.springframework.boot</groupId>
                                        <artifactId>spring-boot-maven-plugin</artifactId>
                                      </plugin>
                                    </plugins>
                                  </build>`)
                }


                return _this.formatXml((new XMLSerializer()).serializeToString(xmlDoc));
            },
            download: function () {
                var zip = new JSZip();
                zip.file("Hello.txt", "Hello World\n");
                // var img = zip.folder("images");
                // img.file("smile.gif", imgData, {base64: true});
                zip.generateAsync({type: "blob"})
                    .then(function (content) {
                        // see FileSaver.js
                        // saveAs(content, "example.zip");
                    });
            }
        }
    })
;