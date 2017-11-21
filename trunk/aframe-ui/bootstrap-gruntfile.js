'use strict';

module.exports = function (grunt) {

    // Automatically load required Grunt tasks
    require('jit-grunt')(grunt);

    var cfg = {
        src: 'bootstrap',
        dist: 'app/bootstrap'
    };

    var pkg = {
        name: "bootstrap"
    };

    grunt.initConfig({
        cfg: cfg,
        pkg: pkg,

        concat: {
            dist: {
                src: [
                    '<%= cfg.src%>/js/transition.js',
                    '<%= cfg.src%>/js/alert.js',
                    '<%= cfg.src%>/js/button.js',
                    '<%= cfg.src%>/js/carousel.js',
                    '<%= cfg.src%>/js/collapse.js',
                    '<%= cfg.src%>/js/dropdown.js',
                    '<%= cfg.src%>/js/modal.js',
                    '<%= cfg.src%>/js/tooltip.js',
                    '<%= cfg.src%>/js/popover.js',
                    '<%= cfg.src%>/js/scrollspy.js',
                    '<%= cfg.src%>/js/tab.js',
                    '<%= cfg.src%>/js/affix.js',
                    '<%= cfg.src%>/js/sidebar.js'
                ],
                dest: '<%= cfg.dist%>/js/<%=pkg.name%>.js'
            }
        },

        less: {
            core: {
                options: {
                    strictMath: true,
                    sourceMap: true,
                    outputSourceFiles: true,
                    sourceMapURL: '<%=pkg.name%>.css.map',
                    sourceMapFilename: '<%= cfg.dist%>/css/bootstrap.css.map'
                },
                src: '<%= cfg.src%>/less/<%=pkg.name%>.less',
                dest: '<%= cfg.dist%>/css/<%=pkg.name%>.css'
            },
            theme: {
                options: {
                    strictMath: true,
                    sourceMap: true,
                    outputSourceFiles: true,
                    sourceMapURL: '<%=pkg.name%>-theme.css.map',
                    sourceMapFilename: '<%= cfg.dist%>/css/<%=pkg.name%>-theme.css.map'
                },
                src: '<%= cfg.src%>/less/theme.less',
                dest: '<%= cfg.dist%>/css/<%=pkg.name%>-theme.css'
            }
        },

        autoprefixer: {
            options: {
                browsers: ['last 1 version']
            },
            core: {
                options: {
                    map: true
                },
                src: '<%= cfg.dist%>/css/<%=pkg.name%>.css'
            },
            theme: {
                options: {
                    map: true
                },
                src: '<%= cfg.dist%>/css/<%= pkg.name %>-theme.css'
            }
        },

        copy: {
            dist: {
                expand: true,
                dot: true,
                cwd: '<%= cfg.src %>',
                dest: '<%= cfg.dist %>',
                src: [
                    '*.html',
                    'fonts/*',
                    'favicon.ico'
                ]
            },
            html: {
                expand: true,
                dot: true,
                cwd: '<%= cfg.src %>',
                dest: '<%= cfg.dist %>',
                src: ['*.html']
            },
            styles: {
                expand: true,
                dot: true,
                cwd: '<%= cfg.src %>/styles',
                dest: '<%= cfg.dist %>/css',
                src: ['*.css']
            }
        },

        jshint: {
            options: {
                jshintrc: '<%=cfg.src%>/js/.jshintrc'
            },
            core: {
                src: '<%=cfg.src%>/js/*.js'
            }
        },

        csslint: {
            options: {
                csslintrc: '<%=cfg.src%>/less/.csslintrc'
            },
            dist: [
                '<%=cfg.dist%>/css/bootstrap.css',
                '<%=cfg.dist%>/css/bootstrap-theme.css'
            ]
        },

        csscomb: {
            options: {
                config: '<%=cfg.src%>less/.csscomb.json'
            },
            dist: {
                expand: true,
                cwd: '<%=cfg.dist%>/css/',
                src: ['*.css', '!*.min.css'],
                dest: '<%=cfg.dist%>/css/'
            }
        },

        clean: {
            dist: {
                files: [{
                    dot: true,
                    src: ['<%= cfg.dist %>/**'
                    ]
                }]
            }
        },

        /*connect: {
            options: {
                port: cfg.serverPort,
                hostname: cfg.serverHost,
                livereload: cfg.livereload
            },
            livereload: {
                options: {
                    open: true,
                    middleware: function (connect, options) {
                        return [
                            connect.static(cfg.dist),
                            connect().use('/bower_components', connect.static('./bower_components'))
                        ];
                    }
                }
            }
        },

        watch: {
            options: {
                livereload: cfg.livereload
            },
            js: {
                files: [cfg.src + '/js/!**'],
                tasks: ['dist-js']
            },
            less: {
                files: [cfg.src + '/less/!**!/!*.less'],
                tasks: ['dist-css']
            },
            html: {
                files: [cfg.src + '/!*.html'],
                tasks: ['copy:html']
            },
            styles: {
                files: [cfg.src + '/styles/!*.css'],
                tasks: ['copy:styles']
            }
        }*/
    });

    require('time-grunt')(grunt);

    grunt.registerTask("dist-js", ['concat', 'jshint']);

    grunt.registerTask("dist-css", ['less', 'autoprefixer'/*, 'csscomb:dist'*/]);

    grunt.registerTask("build", ['clean', 'dist-js', 'dist-css', 'copy']);

    //grunt.registerTask("default", ['clean', 'dist-js', 'dist-css', 'copy', 'connect:livereload', 'watch']);
};
