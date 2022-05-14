const {defineConfig} = require("@vue/cli-service");
module.exports = {
    // 빌드시 생성 위치
    outputDir: "../../resources/static",
    // index 파일 위치
    indexPath: "./public/index.html",
    // SpringBoot 서버 주소
    devServer: {
        proxy: "http://localhost:8000"
    },
    chainWebpack: config => {
        const svgRule = config.module.rule("svg");
        svgRule.uses.clear();
        svgRule.use("vue-svg-loader").loader("vue-svg-loader");
    }
};
