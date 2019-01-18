<!DOCTYPE html>
<html lang="en">
    <body>

        <form id="form-with-annotation" action="./process-with-annotation" method="post">
            <input id="token-with-annotation" type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
            <input id="input-with-annotation" type="text" name="name">
            <input id="submit-with-annotation" type="submit">
        </form>

        <form id="form-no-annotation" action="./process-no-annotation" method="post">
            <input id="token-no-annotation" type="hidden" name="${mvc.csrf.name}" value="${mvc.csrf.token}"/>
            <input id="input-no-annotation" type="text" name="name">
            <input id="submit-no-annotation" type="submit">
        </form>

    </body>
</html>