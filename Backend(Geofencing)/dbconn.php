<?php

require __DIR__ . '/vendor/autoload.php';

use Kreait\Firebase\Factory;

$factory = (new Factory())
    ->withProjectId('data.json')
    ->withDatabaseUri('https://gps-b123c-default-rtdb.firebaseio.com//');

$database = $factory->createDatabase();
