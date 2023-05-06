# Vue教程

Vue.js 是一套构建用户界面的渐进式框架。Vue 只关注视图层， 采用自底向上增量开发的设计。

## Vue安装

- 独立版本

可以在 Vue.js 的官网上直接下载最新版本, 并用 `<script>` 标签引入。

```html
<script src="js/vue.js"></script>
```

- CDN（Content Delivery Network，即内容分发网络）方法
  - unpkg：https://unpkg.com/vue@next, 会保持和 npm 发布的最新的版本一致。
  - cdnjs : https://cdnjs.cloudflare.com/ajax/libs/vue/3.0.5/vue.global.js

```html
<script src="https://cdn.staticfile.org/vue/3.0.5/vue.global.js"></script>
</head>
```

- NPM方法

由于 npm 安装速度慢，使用淘宝镜像：

```sh
npm config set registry https://registry.npm.taobao.org
```

```sh
#升级 npm
cnpm install npm -g
# 升级或安装 cnpm
npm install cnpm -g
```

```sh
$ npm init vue@latest
```

上述命令安装并执行 create-vue（ Vue官方的项目脚手架工具）。

在项目被创建后，通过以下步骤安装依赖并启动开发服务器：

```sh
$ cd runoob-vue3-test
$ npm install
$ npm run dev
  VITE v4.3.4  ready in 543 ms

  ➜  Local:   http://localhost:5173/
  ➜  Network: use --host to expose
  ➜  press h to show help
```

- 打包

打包 Vue 项目使用以下命令：

```sh
cnpm run build
```

## 创建项目

- `vue create`命令

```sh
vue create [options] <app-name>
```

执行以上命令会出现安装选项界面：

```sh
Vue CLI v4.4.6
? Please pick a preset: (Use arrow keys)
❯ default (babel, eslint)
  Manually select features
```

安装完成后，我们进入项目目录：

```sh
cd test-app2
npm run serve
```

- `vue ui`命令

```sh
C:\Users\86158>vue ui
🚀  Starting GUI...
🌠  Ready on http://localhost:8000
```

执行以上命令，会在浏览器弹出一个项目管理的界面，可以点击创建选项来创建一个项目，选择底部"在此创建项目"，页面上方也可以选择路径。

创建项目时长时间停留在`Installing CLI plugins`步骤可能

## 目录结构

| 目录/文件      | 说明                                                         |
| :------------- | :----------------------------------------------------------- |
| `build`        | 项目构建(webpack)相关代码                                    |
| `config`       | 配置目录，包括端口号等。我们初学可以使用默认的。             |
| `node_modules` | npm 加载的项目依赖模块                                       |
| `src`          | 这里是我们要开发的目录，基本上要做的事情都在这个目录里。里面包含了几个目录及文件：assets: 放置一些图片，如logo等。components: 目录里面放了一个组件文件，可以不用。App.vue: 项目入口文件，我们也可以直接将组件写这里，而不使用 components 目录。main.js: 项目的核心文件。index.css: 样式文件。 |
| `static`       | 静态资源目录，如图片、字体等。                               |
| `public`       | 公共资源目录。                                               |
| `test`         | 初始测试目录，可删除                                         |
| `.xxxx`文件    | 配置文件，包括语法配置，git配置等。                          |
| `index.html`   | 首页入口文件                                                 |
| `package.json` | 项目配置文件。                                               |
| `README.md`    | 项目的说明文档，markdown 格式                                |
| `dist`         | 使用`npm run build` 命令打包后会生成该目录。                 |

## 指令

Vue 指令（Directives）是 Vue.js 的一项核心功能，它们可以在 HTML 模板中以 `v-` 开头的特殊属性形式使用，用于将响应式数据绑定到 DOM 元素上或在 DOM 元素上进行一些操作。

以下是几个常用的 Vue 指令：

| 指令      | 描述                                                         |
| :-------- | :----------------------------------------------------------- |
| `v-bind`  | 用于将 Vue 实例的数据绑定到 HTML 元素的属性上。              |
| `v-if`    | 用于根据表达式的值来条件性地渲染元素或组件。                 |
| `v-show`  | v-show 是 Vue.js 提供的一种指令，用于根据表达式的值来条件性地显示或隐藏元素。 |
| `v-for`   | 用于根据数组或对象的属性值来循环渲染元素或组件。             |
| `v-on`    | 用于在 HTML 元素上绑定事件监听器，使其能够触发 Vue 实例中的方法或函数。 |
| `v-model` | 用于在表单控件和 Vue 实例的数据之间创建双向数据绑定。        |

```html
<div id="app" class="demo">
    <input type="text" v-model="message">
    <p>{{ message }}</p>
</div>

<script>
    const HelloVueApp = {
        data() {
            return {
                message: 'Hello Vue!'
            }
        }
    }
    <!-- 将 Vue 应用 HelloVueApp 挂载到 <div id="hello-vue"></div> 中 -->
    Vue.createApp(HelloVueApp).mount('#app')
</script>
```

