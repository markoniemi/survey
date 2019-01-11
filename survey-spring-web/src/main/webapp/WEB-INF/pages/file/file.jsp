<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title><spring:message code="file" /></title>
<base href="${pageContext.request.contextPath}/" />
<link rel='stylesheet' type="text/css" href='webjars/bootstrap/css/bootstrap.min.css'></link>
<link rel='stylesheet' type="text/css" href='webjars/font-awesome/css/all.min.css'></link>
</head>
<body>
    <script type="text/javascript" src="webjars/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="webjars/bootstrap/js/bootstrap.bundle.min.js"></script>

    <c:import url="../common/menu.jsp"></c:import>

    <form class="form-horizontal" method="POST" enctype="multipart/form-data" action="file/save">
        <div class="form-group">
            <label class="col-md-4 control-label" for="file"><spring:message code="fileUpload" /></label>
            <div class="input-group md-4">
                <div class="custom-file">
                    <input type="file" class="custom-file-input" id="file" name="file"><label class="custom-file-label btn"
                        for="file"><spring:message code="fileToUpload" /></label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <button id="submit" name="" class="btn btn-primary">
                <span class="fas fa-check"></span>
            </button>
        </div>
    </form>
    <script>
          $('#file').on('change', function() {
            //get the file name
            var fileName = $(this).val();
            //replace the "Choose a file" label
            $(this).next('.custom-file-label').html(fileName);
          });
        </script>
</body>
</html>