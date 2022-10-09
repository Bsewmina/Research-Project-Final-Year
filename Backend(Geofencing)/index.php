<?php
include "header.php";
include "dbconn.php";
session_start();
?>
<?php

$ref = $database->getReference('latitude');
$lati = $ref->getSnapshot()->getValue();

$ref = $database->getReference('longitude');
$longi = $ref->getSnapshot()->getValue();

//echo $longi;
//echo $lati;


$ref = $database->getReference('vehicle_Data/');
$Array = $ref->getSnapshot()->getValue();
foreach ($Array as $key => $value)

$string = $value['longitude'];
$vertices_x = preg_split ("/\,/", $string); 
//print_r($vertices_x);


$ref = $database->getReference('vehicle_Data/');
$Array2 = $ref->getSnapshot()->getValue();
foreach ($Array2 as $key => $value2)

$string2 = $value2['latitude'];
$vertices_y = preg_split ("/\,/", $string2); 
//print_r($vertices_y);


//longi
//$vertices_x = array(79.993772, 80.005102, 80.005102, 79.993772);    // x-coordinates of the vertices of the polygon
//lati
//$vertices_y = array(6.912367,6.912367,-6.922336,6.922336); // y-coordinates of the vertices of the polygon

$points_polygon = count($vertices_x) - 1;  // number vertices - zero-based array
$longitude_x = $longi;  
$latitude_y = $lati;    

if (is_in_polygon($points_polygon, $vertices_x, $vertices_y, $longitude_x, $latitude_y)){
    $status = "inside";
    //$result = $database-> ('status')->push($status);
    $database->getReference('status')->set($status);
  //echo "Is in polygon!";
}
else{
    $status = "Outside";
    //echo "Is not in polygon";
    $database->getReference('status')->set($status);
    //$result = $database->getReference('status')->push($status);
}



function is_in_polygon($points_polygon, $vertices_x, $vertices_y, $longitude_x, $latitude_y)
{
  $i = $j = $c = 0;
  for ($i = 0, $j = $points_polygon ; $i < $points_polygon; $j = $i++) {
    if ( (($vertices_y[$i]  >  $latitude_y != ($vertices_y[$j] > $latitude_y)) &&
     ($longitude_x < ($vertices_x[$j] - $vertices_x[$i]) * ($latitude_y - $vertices_y[$i]) / ($vertices_y[$j] - $vertices_y[$i]) + $vertices_x[$i]) ) )
       $c = !$c;
  }
  return $c;
}

?>


<div class="container">
    <div class="my-3">
        <div class="card">
            <div class="card-body">
                <a href="/backendPHP/add_new.php" class="btn btn-primary">Add New</a>
                <a href="https://geojson.io/#map=8/8.157/80.236" target="_blank" class="btn btn-success float-right">Create Geofence >>> </a>
            </div>
        </div>
    </div>
    <div class="my-3">
        <?php
        if (isset($_SESSION['message'])) {
        ?>
            <div class="alert alert-primary" role="alert">
                <?php
                echo $_SESSION['message'];
                ?>
            </div>
        <?php
        }
        unset($_SESSION['message']);
        ?>

    </div>
    <table class="table">
        <thead>
            <tr>
                <!--<th scope="col">#</th>-->
                <th scope="col">Vehicle No</th>
                <th scope="col">Route No</th>
                <th scope="col">Longitude cordinates</th>
                <th scope="col">Latitude cordinates</th>
                <th scope="col">Status</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
            </tr>
        </thead>
        <tbody>
            <?php

            $ref = $database->getReference('vehicle_Data');
            $vehicle_Data = $ref->getSnapshot()->getValue();

            foreach ($vehicle_Data as $key => $value) {
            ?>
                <tr>
                    <!--<th scope="row"><?php echo $key ?></th>-->
                    <td><?php echo $value['Vehicle_No'] ?></td>
                    <td><?php echo $value['Route_No'] ?></td>
                    <td><?php echo $value['longitude'] ?></td>
                    <td><?php echo $value['latitude'] ?></td>
                    <td><?php echo $status ?></td>
                    <td> <a href="/backendPHP/update-data.php?key=<?php echo $key ?>" class="btn btn-primary">Edit</a></td>
                    <td>
                        <a href="/backendPHP/delete-data.php?key=<?php echo $key ?>" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            <?php } ?>
        </tbody>
    </table>
</div>




<?php
include "footer.php";
?>