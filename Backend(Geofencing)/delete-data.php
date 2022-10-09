<?php
include "dbconn.php";
session_start();

if (isset($_GET['key'])) {
    $key = $_GET['key'];

    $products = $database->getReference('vehicle_Data/' . $key)->remove();
    if ($products) {
        $_SESSION['message'] = "Deleted Successfully";
        header('Location:index.php');
    }
}
