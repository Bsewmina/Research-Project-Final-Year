<?php
include "header.php";
include "dbconn.php";
session_start();

if (isset($_POST['save'])) {
    $Vehicle_No = $_POST['Vehicle_No'];
    $Route_No = $_POST['Route_No'];
    $longitude = $_POST['longitude'];
    $latitude = $_POST['latitude'];
    $key = $_POST['key'];
    $data = [
        "Vehicle_No" => $Vehicle_No,
        "Route_No" => $Route_No,
        "longitude" => $longitude,
        "latitude" => $latitude
    ];

    $result = $database->getReference('vehicle_Data/' . $key)->update($data);
    if ($result) {
        $_SESSION['message'] = "updated Successfully";
        header("Location:index.php");
    }
}

if (isset($_GET['key'])) {
    $vehicle_Data = $database->getReference('vehicle_Data')->getChild($_GET['key'])->getValue();
?>
    <div class="container my-4">
        <div class="row d-flex justify-content-center">
            <div class="col-xl-5 col-lg-6 col-md-8 col-sm-12">

                <div class="card">
                    <div class="card-header">
                        <h2>Update Vehicle Data</h2>
                    </div>
                    <div class="card-body">
                        <form action="<?php $_SERVER['PHP_SELF'] ?>" method="POST">
                            <div class="mb-3">
                                <label for="" class="form-label">Vehicle No</label>
                                <input type="text" class="form-control" id="" value="<?php echo $vehicle_Data["Vehicle_No"] ?>" name="Vehicle_No">
                                <input type="hidden" class="form-control" id="" value="<?php echo $_GET['key'] ?>" name="key">
                            </div>
                            <div class="mb-3">
                                <label for="" class="form-label">Route No</label>
                                <input type="number" class="form-control" id="" value="<?php echo $vehicle_Data['Route_No'] ?>" name="Route_No">
                            </div>
                            <div class="mb-3">
                                <label for="" class="form-label">Longitude</label>
                                <input type="text" class="form-control" id="" value="<?php echo $vehicle_Data['longitude'] ?>" name="longitude">
                            </div>
                            <div class="mb-3">
                                <label for="" class="form-label">Latitude</label>
                                <input type="text" class="form-control" id="" value="<?php echo $vehicle_Data['latitude'] ?>" name="latitude">
                            </div>
                            <button type="submit" name="save" class="btn btn-primary">Update</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
<?php
}
include "footer.php";
?>