<?php
include "header.php";
include "dbconn.php";
session_start();

if (isset($_POST['save'])) {
    //$title = $_POST['title'];
    $route_number = $_POST['route_number'];
    $cordinates = $_POST['cordinates'];
    $data = [
        //"title" => $title,
        "route_number" => $route_number,
        "cordinates" => $cordinates
    ];

    $result = $database->getReference('products')->push($data);
    if ($result) {
        $_SESSION['message'] = " Add Successfully";
        header("Location:index.php");
    }
}
?>
<div class="container">
    <div class="row d-flex justify-content-center">
        <div class="col-xl-5 col-lg-6 col-md-8 col-sm-12">

            <div class="card">
                <div class="card-header">
                    <h2>Create Product</h2>
                </div>
                <div class="card-body">
                    <form action="<?php $_SERVER['PHP_SELF'] ?>" method="POST">
                        <!--<div class="mb-3">
                            <label for="" class="form-label">Product Title</label>
                            <input type="text" class="form-control" id="" name="title">
                        </div>-->
                        <div class="mb-3">
                            <label for="" class="form-label">Route Number</label>
                            <input type="number" class="form-control" id="" name="price">
                        </div>
                        <div class="mb-3">
                            <label for="" class="form-label">Cordinates</label>
                            <input type="text" class="form-control" id="" name="content">
                        </div>
                        <button type="submit" name="save" class="btn btn-primary">Save</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<?php
include "footer.php";
?>