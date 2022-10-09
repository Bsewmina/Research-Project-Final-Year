<?php
include "header.php";
include "dbconn.php";
session_start();

if (isset($_POST['save'])) {
    $Vehicle_No = $_POST['Vehicle_No'];
    $Route_No = $_POST['Route_No'];
    $longitude = $_POST['longitude'];
    $latitude = $_POST['latitude'];
    $data = [
        "Vehicle_No" => $Vehicle_No,
        "Route_No" => $Route_No,
        "longitude" => $longitude,
        "latitude" => $latitude
    ];

    $result = $database->getReference('vehicle_Data')->push($data);
    if ($result) {
        $_SESSION['message'] = "Added Successfully";
        header("Location:index.php");
    }
}
?>
<div class="container">
    <div class="row d-flex justify-content-center">
        <div class="col-xl-5 col-lg-6 col-md-8 col-sm-12">

            <div class="card">
                <div class="card-header">
                    <h2>Add Vehicle Data</h2>
                </div>
                <div class="card-body">
                    <form action="<?php $_SERVER['PHP_SELF'] ?>" method="POST">
                        <div class="mb-3">
                            <label for="" class="form-label">Vehicle No</label>
                            <input type="text" class="form-control" id="" name="Vehicle_No">
                        </div>
                        <div class="mb-3">
                            <label for="" class="form-label">Route No</label>
                            <input type="number" class="form-control" id="" name="Route_No">
                        </div>
                        <div class="mb-3">
                            <label for="" class="form-label">Longitude</label>
                            <input type="text" class="form-control" id="" name="longitude">
                        </div>
                        <div class="mb-3">
                            <label for="" class="form-label">Latitude</label>
                            <input type="text" class="form-control" id="" name="latitude">
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