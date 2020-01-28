<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>下载指数</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <script src="jquery/jquery-3.3.1.min.js"></script>
    <script src="datepicker/js/bootstrap-datepicker.min.js"></script>
    <script src="datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
    <link rel="stylesheet" href="datepicker/css/bootstrap-datepicker.css">
</head>
<body>
<div class="container">
    <h1>导出”百度指数“到Excel</h1>
    <form action="downloadExcel">
        词语：<input type="text" name="word" placeholder="请输入你要检索的词语" required class="form-control">
        开始日期：<input type="text" name="startDate" class="form-control datePicker" required>
        结束日期：<input type="text" name="endDate" class="form-control datePicker" required><br/>
        <input type="submit" value="导出数据到Excel" class="btn btn-dark">
    </form>
</div>
</body>
<script>
    $('.datePicker').datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        orientation: "bottom right",
        todayBtn: "linked",
        clearBtn: true
    });
</script>
</html>