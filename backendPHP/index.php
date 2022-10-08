<?php
include "header.php";
include "dbconn.php";
session_start();
?>


<div class="container">
    <div class="my-3">
        <div class="card">
            <div class="card-body">
                <a href="/backendPHP/new-product.php" class="btn btn-primary">New</a>
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
                <th scope="col">#</th>
                <!--<th scope="col">Number Plate</th>-->
                <th scope="col">Route Number</th>
                <th scope="col">Cordinates</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
            </tr>
        </thead>
        <tbody>
            <?php

            $ref = $database->getReference('products');
            $products = $ref->getSnapshot()->getValue();

            foreach ($products as $key => $value) {
            ?>
                <tr>
                    <th scope="row"><?php echo $key ?></th>
                    <td><?php echo $value['route_number'] ?></td>
                    <td><?php echo $value['cordinates'] ?></td>
                    <td> <a href="/backendPHP/update-product.php?key=<?php echo $key ?>" class="btn btn-primary">Edit</a></td>
                    <td>
                        <a href="/backendPHP/delete-product.php?key=<?php echo $key ?>" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            <?php } ?>
        </tbody>
    </table>
</div>


<?php
include "footer.php";
?>