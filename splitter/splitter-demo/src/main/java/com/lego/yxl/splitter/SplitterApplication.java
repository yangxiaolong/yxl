package com.lego.yxl.splitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s?__biz=MzA3Mzk4Mzc1Mw==&mid=2247483850&idx=1&sn=1582a8720addc14f93bfddf60013eea8&chksm=9f07f3aca8707aba407d3a5db41d815c202253a91dbe226b5322da7121546b20b067edb6de7e&scene=126&sessionid=1727687467&subscene=7&clicktime=1727699944&enterid=1727699944&key=daf9bdc5abc4e8d0b43cafe056ca95c25af6a1a2071947f238a7c6668b83262ce3affdf495a4ed4b46b5ef50ff9acd6199bcd6e9885e7bd4bdafe2570ef777fd8b7b624da0214624c10d968ba1449c9b7e3fce3bbe593422e6fd89b061a78d94c6552edbe7f631e285bdada6b7735320c9c5626ae1f1ed99a512c6a24ead443f&ascene=0&uin=MTU0NTU2ODkyNg%3D%3D&devicetype=Windows+10+x64&version=63090c0f&lang=zh_CN&countrycode=CN&exportkey=n_ChQIAhIQne%2Btx6JBG4Pa3dy1brDMBRLmAQIE97dBBAEAAAAAAMfJFLUkgKgAAAAOpnltbLcz9gKNyK89dVj0GKUaC8Nb%2BMIWFT4wfyGP806ARxqQN%2FHsFouvkth4k%2Botzr2lzIEMRTT1lt9W%2FZ7Ck2x95ruPWx4kg31D%2F2dxFYj%2F%2FtAjimOnLr%2Bw%2BBFHYbumzKDk0evcNSfyc2qXWjqu3wVhsvX9MhHy2Xo5PTgbYFPMJkUCNG%2BmHgNCg3Ul%2FBWdepVEF7naiHratuomxYXB%2FptutbErWQLHsEhMsdxgtHgQqvNFqqPUQiv8RRuFI88VfEK%2FbVjy%2BRL9Cu2fHjG8&acctmode=0&pass_ticket=%2BM2di3d8KVutTGbGGhlp8lHkh4UEEx68CG426O95Srdr9j0K3OnydZ7xORmbvrjT&wx_header=1
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SplitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitterApplication.class, args);
    }

}
