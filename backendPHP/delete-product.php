<?php
include "dbconn.php";
session_start();

if (isset($_GET['key'])) {
    $key = $_GET['key'];

    $products = $database->getReference('products/' . $key)->remove();
    if ($products) {
        $_SESSION['message'] = "Delete Successfully";
        header('Location:index.php');
    }
}
