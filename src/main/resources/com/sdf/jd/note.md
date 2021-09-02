####chrome driver
> https://chromedriver.storage.googleapis.com/index.html?path=93.0.4577.15/
#### selenuim
```
System.getProperties().setProperty("webdriver.chrome.driver", "com/sdf/jd/chromedriver");
WebDriver webDriver = new ChromeDriver();
webDriver.get("http://huaban.com/");
WebElement webElement = webDriver.findElement(By.xpath("/html"));
System.out.println(webElement.getAttribute("outerHTML"));
webDriver.close();
```

### 常见问题
```
  The driver is not executable: /usr/bin/chromedriver
    执行命令：
    chmod a+x chromedriver
    赋予可执行
```