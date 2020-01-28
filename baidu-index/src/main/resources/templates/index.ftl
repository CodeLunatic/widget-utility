<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <#if isError??>
        <h1 style="color: #ff0000;">程序出错了，请检查BDUSS</h1>
    <#else>
        <h1>在开始使用程序之前</h1>
    </#if>
    <h4>你需要复制BDUSS到下面的输入框中，以保持你和百度指数的登陆状态</h4><br>
    <form action="bduss" class="form-group">
        <input type="text" name="bduss" placeholder="请复制你的BDUSS到输入框中" class="form-control"><br>
        <input type="submit" value="进入程序" class="btn btn-danger">
    </form>
</div>
</body>
</html>