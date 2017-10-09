let path = require('path');
let webpack = require('webpack');

module.exports = {
    devtool: 'source-map',
    entry: './src/index.js',
    output: {
        path: path.join(__dirname, '../static/js'),
        filename: 'bundle.js'
    },
    resolve: {extensions: ['.js', '.jsx', '.css']},
    plugins: [
        new webpack.LoaderOptionsPlugin({
            debug: true
        }),
        new webpack.DefinePlugin({
            "process.env": {
                NODE_ENV: JSON.stringify("development")
            }
        })
    ],
    module: {
        rules: [
            {
                test: /\.(png|jpg|jpeg|gif|svg|woff|woff2)$/,
                loader: 'url-loader?limit=100000',
            },
            {
                test: /\.(eot|ttf|wav|mp3)$/,
                loader: 'file-loader',
            },
            {
                test: /\.css$/,
                loaders: 'style-loader!css-loader',
            },
            {
                test: /\.jsx?$/,
                loader: 'babel-loader',
                exclude: /node_modules/
            }
        ]
    },
    devServer: {
        noInfo: false,
        quiet: false,
        lazy: false,
        publicPath: "/js/",
        filename: "bundle.js",
        compress: true,
        watchOptions: {
            poll: true
        }
    }
};