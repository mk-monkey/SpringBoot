/*
 * @Author: M_King 
 * @Date: 2017-09-20 20:10:45 
 * @Last Modified by: M_King
 * @Last Modified time: 2017-10-26 20:18:49
 */

//移动端基础效果
function mobileModel(width) {
    width = width || 750;
    document.body.style['margin'] = '0 auto';
    autoWidth();

    function autoWidth() {
        var winwidth = window.innerWidth; //获取窗口宽度
        winwidth = winwidth > width ? width : winwidth; //判断窗口宽度
        //设置基础字体大小
        document.body.style['font-size'] = document.querySelector('html').style['font-size'] = winwidth / 30 + 'px';
        document.body.style['width'] = winwidth + 'px';
    }

    //窗口大小改变时更新宽度
    window.addEventListener('resize', function() {
        autoWidth();
    }, false);
}

/**
 * 计算时间差
 * 
 * @param {any} startDate 开始时间 (格式：2015-10-10)
 * @param {any} endDate 结束时间 (格式：2015-10-10)
 * @returns 时间差
 */
function getDateDiff(startDate, endDate) {
    //获取时间
    var startTime = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
    var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
    //计算时间差
    var dates = Math.abs(endTime - startTime) / (1000 * 60 * 60 * 24);
    //返回时间差
    if (startTime > endTime) {
        return -Math.ceil(dates);
    } else {
        return Math.ceil(dates);
    }
}

//弹窗对象
/**
 * 弹窗对象
 * 
 * @param {any} options 
 */
function Popup(options) {
    //默认参数
    var defaults = {
        'id': 'popup', //弹窗id
        'class': 'popup', //弹窗样式
        'wrap': document.body, //弹窗外层
        'close': true, //可关闭弹窗
        'title': '提示', //弹窗标题
        'content': '弹窗内容' //弹窗内容
    };
    //写入参数
    this.setOptions(options, defaults);
}
//设置参数
Popup.prototype.setOptions = function(options, defaults) {
    options = options || {};
    defaults = defaults || this.settings;
    //参数对比
    this.settings = dataExtend(options, defaults);
    console.log('Popup setOptions:', this.settings);
    this.createPopup();
};
//创建弹窗
Popup.prototype.createPopup = function() {
    //创建弹窗主对象
    var main = document.createElement('div');
    main.id = this.settings.id;
    main.className = this.settings.class + ' hide';
    main.innerHTML = '<div class="popup_bg"></div><div class="popup_body"></div>';
    this.settings.wrap.appendChild(main);

    //获取DOM
    var bg = main.getElementsByClassName('popup_bg')[0];
    var body = main.getElementsByClassName('popup_body')[0];

    //判断弹窗是否可关闭
    if (this.settings.close) {
        bg.addEventListener('click', function() {
            this.hide();
        }.bind(this), false);
    }

    this.dom = {
        'main': main,
        'bg': bg,
        'body': body
    };

    //判断是否有title
    if (this.settings.title.length) {
        var title = createElement({
            'className': 'popup_title',
            'innerHTML': this.settings.title
        });

        this.dom.title = this.dom.body.appendChild(title);
    }

    //创建DOM
    var content = createElement({
        'className': 'popup_content',
        'innerHTML': this.settings.content
    });

    this.dom.content = this.dom.body.appendChild(content);
};
//显示弹窗
Popup.prototype.show = function() {
    console.log('Popup show');
    this.dom.main.classList.remove('hide');
};
//隐藏弹窗
Popup.prototype.hide = function() {
    console.log('Popup hide');
    this.dom.main.classList.add('hide');
};
//创建元素
function createElement(options) {
    options = options || {};
    var defaults = {
        'id': null,
        'className': null,
        'tag': 'div',
        'innerHTML': null
    };
    var settings = dataExtend(options, defaults);

    var element = document.createElement(settings.tag);

    var attr = ['id', 'className', 'innerHTML'];

    attr.forEach(function(string) {
        if (settings[string]) {
            element[string] = settings[string];
        }
    });

    return element;
}

/**
 * 获取url参数
 * 
 * @param {any} name 参数名称
 * @returns 参数值
 */
function getUrlParam(name) {
    var regexp = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(regexp);
    if (result !== null) return unescape(result[2]);
    return null;
}

//表单验证
/*
	参数说明
	from：验证的表单
	error：错误函数
	success：成功函数
	post:提交函数
*/
function verification(form, error, success, post) {
    //类名数组
    /* is_user:用户名,is_qq:QQ号,is_tell:固话号码,is_mobile:手机号码,is_id:身份证号码,is_email:邮箱,is_pass:密码,is_bank:银行卡,is_num:纯数字,is_ch:纯中文,is_ip:IP*/
    var name = [
        "is_name",
        "is_qq",
        "is_tell",
        "is_mobile",
        "is_id",
        "is_email",
        "is_pass",
        "is_bank",
        "is_num",
        "is_ch",
        "is_empty"
    ];
    //正则表达式数组
    var regex = [
        /[^a-zA-Z0-9]/,
        /^\d{5,11}$/,
        /^0\d{2,3}-{0,1}\d{7,8}$|^\d{7,8}$/,
        /((1[34578][0-9]{1}))\d{8}/,
        /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
        /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/,
        /\d+[a-zA-Z]+|[a-zA-Z]+\d+/,
        /^\d{19}$|^\d{12}$|^\d{16}$/,
        /[^0-9]/,
        /[\u4E00-\u9FFF]/,
        /[\u4E00-\u9FFF]/
    ];
    //反向数组
    var rev = [0, 8];
    //获取要验证的对象
    var obj = $("[ver]");
    //ver转换成验证序号
    for (i = 0; i < obj.length; i++) {
        for (j = 0; j < name.length; j++) {
            if (name[j] == obj.eq(i).attr("ver")) {
                obj.eq(i).attr("ver", j);
                break;
            }
        }
    }
    //失去焦点
    obj.bind("blur", function() {
        var val = $(this).val(); //获取当前对象value
        var dat = $(this).attr("ver"); //获取当前对象ver
        //判断value是否为空
        if (val === "") {
            error(this);
            return false;
        }
        //正则验证
        if (test(val, dat)) {
            success(this);
        } else {
            error(this);
        }
    });
    //测试方法
    function test(value, num) {
        var temp = regex[num].test(value); //验证正则表达式
        //判断取反
        for (i = 0; i < rev.length; i++) {
            if (num == rev[i]) {
                temp = !temp;
                break;
            }
        }
        //判断对错
        if (temp) {
            return true;
        } else {
            return false;
        }
    }
    //提交
    form.bind("submit", function() {
        var t = $(this).find("[ver]");
        t.blur(); //触发失去焦点事件
        var err = $(this).find("[error='on']");
        if (err.length) {
            err.eq(0).blur();
        } else {
            //提交
            post();
            return true;
        }
        return false;
    });
}

/**
 * 生成不重复随机字符串
 * 
 * @param {number} count 数量
 * @returns 随机字符串数组
 */
function randomString(count) {
    var array = [];
    for (var i = 0; i < count; i++) {
        var n = "n" + Math.random().toString(36).substr(2, 7);
        //判断生成的字符串是否重复
        if (array.indexOf(n) === -1) {
            array.push(n);
        } else {
            i--;
        }
    }
    return array;
}

// 数组去重
Array.prototype.unique = function() {
    var result = [];
    this.forEach(function(element) {
        if (result.indexOf(element) === -1) {
            result.push(element);
        }
    });
    return result;
};

// 数组乱序
Array.prototype.randomSort = function() {
    var array = this.concat([]),
        index = array.length;
    //开始遍历
    for (var i = array.length; i > 0; i--) {
        var random = parseInt(Math.random() * index);
        index--;
        //交换位置
        var last = array[index];
        array[index] = array[random];
        array[random] = last;
    }
    return array;
};

//统计代码的运行时间
function Running(action) {
    var start = Date.now();
    action();
    console.log("Runing time", Date.now() - start + "ms");
}

/**
 * 参数对比
 * 
 * @param {any} data 参数
 * @param {any} defaults 默认值
 * @param {array} exception 例外：['data']
 * @returns 对比后的参数
 */
function dataExtend(data, defaults, exception) {
    exception = exception || [];
    var result = {};
    for (var k in defaults) {
        if (!defaults.hasOwnProperty(k)) {
            continue;
        }
        if (typeof data[k] == 'undefined') {
            result[k] = defaults[k];
        } else if (exception.indexOf(k) > -1) {
            result[k] = data[k];
        } else if (typeof data[k] == 'object' && !(data[k] instanceof Array) && !(data[k] instanceof HTMLElement)) {
            result[k] = dataExtend(data[k], defaults[k]);
        } else {
            result[k] = data[k];
        }
    }
    return result;
}

/**
 * 计时器
 * 
 * @param {any} options 
 */
function Timer(options) {
    var defaults = {
        'delay': 1000, //时间间隔
        'speed': 1, //步数
        'start': 0, //开始值
        'end': 0, //结束值，正计时下，0为无限计时
        'reverse': false, //是否反向
        'auto_start': true,
        'action': console.log, //默认执行方法
        'before': function() { console.log('Timer before'); }, //默认计时前方法
        'overtime': function() { console.log('Timer overtime'); } //默认超时方法
    };
    //写入设置数据
    this.setOptions(options, defaults);
    //默认状态，true为运行中
    this.status = false;
    console.log('Timer ready');

    //判断是否开始计时器
    if (this.settings.auto_start) {
        this.start();
    }
}
//修改设置
Timer.prototype.setOptions = function(options, defaults) {
    //判断计时器是否在运行中
    if (this.status) {
        console.error('Error: Timer setOptions fail, Timer is runing!');
        return false;
    }
    defaults = defaults || this.settings;
    //参数对比
    this.settings = dataExtend(options, defaults);
    //判断是否倒计时
    if (this.settings.reverse) {
        //倒计时数据
        this.data = {
            'start': this.settings.end + 1,
            'end': this.settings.start + 1,
            'speed': -this.settings.speed
        };
    } else {
        //正计时数据
        this.data = {
            'start': this.settings.start,
            'end': this.settings.end,
            'speed': this.settings.speed
        };
    }
    //初始化计数
    this.count = this.data.start;
    console.log('Timer setOptions:', this.settings);
};
//开始计时器
Timer.prototype.start = function() {
    this.settings.before();
    if (this.status) {
        this.end();
    }
    console.log('Timer start');
    //创建计时器
    this.timer = setInterval(function() {
        //计数增加
        this.count += this.data.speed;
        //调用执行方法
        this.settings.action(this.count);
        //判断是否计时结束
        if (this.isOvertime()) {
            this.settings.overtime();
            this.end();
        }
    }.bind(this), this.settings.delay);
    this.status = true;
};
//结束计时器
Timer.prototype.end = function() {
    //初始化计数
    this.count = this.data.start;
    //清除计时器
    window.clearInterval(this.timer);
    this.status = false;
    // console.log('Timer end');
    return this.count;
};
//判断时候已超时
Timer.prototype.isOvertime = function() {
    if (this.settings.reverse) {
        //倒计时
        return this.count <= this.data.end;
    } else {
        //正计时
        return this.data.end > 0 && this.count >= this.data.end;
    }
};

/**
 * 格式化秒
 * 
 * @param {string} time 秒数
 * @returns 格式化后的日期字符串
 */
function formatSecond(time) {
    time = time < 0 ? 0 : time;
    var date = new Date(time * 1000);
    var action = [date.getSeconds(), date.getMinutes(), date.getHours() - 8];
    var unit = ['秒', '分', '时'];
    var format = '';
    for (var i = 0; i < action.length; i++) {
        if (action[i] > 0) {
            format = action[i] + unit[i] + format;
        }
    }
    return format;
}

/**
 * 序列化获取参数
 * 
 * @param {any} wrap 外层
 * @param {any} data 数据
 * @returns 结果
 */
function formatOptions(wrap, data) {
    //数据类型
    var type = {
        'boolean': function(value) {
            return value == true || value == 'true';
        },
        'number': function(value) {
            if (value == '') {
                return null;
            }
            value = parseInt(value);
            if (!value) {
                return 0;
            }
            return value;
        }
    };
    var result = {};
    //获取数据
    for (var k in data) {
        if (data.hasOwnProperty(k)) {
            var value = wrap.querySelector(data[k].id).value;
            if (type[data[k].type]) {
                result[k] = type[data[k].type](value);
            } else {
                console.error('Error: Type is invalid.');
            }
        }
    }
    return result;
}

/**
 * 写入cookie
 * 
 * @param {any} name 数据名称
 * @param {any} value 数据值
 * @param {any} days 日期，单位：天，不填退出浏览器就失效
 */
function setCookie(name, value, days) {
    if (days && days > 0) {
        var exp = new Date();
        exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    } else {
        document.cookie = name + "=" + escape(value);
    }
}
//读取cookie
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        return unescape(arr[2]);
    } else {
        return null;
    }
}
//删除cookie
function removeCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval = getCookie(name);
    if (cval != null) {
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
}
//清空cookie
function clearCookie() {
    var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if (keys) {
        for (var i = keys.length; i--;) {
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString();
        }
    }
}