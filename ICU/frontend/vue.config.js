module.exports = {
    outputDir: "../../resources/static",
    indexPath: "./public/index.html",
    devServer: {
        proxy: "http://localhost:8000",
        overlay: false
    },
    chainWebpack: config => {
        const svgRule = config.module.rule("svg");
        svgRule.uses.clear();
        svgRule.use("vue-svg-loader").loader("vue-svg-loader");
    }
};
