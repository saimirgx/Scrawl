<?php

require_once 'config.php';

$database = new Database_Queries();

$response = array("error" => FALSE);

if (isset($_POST['email'])) {
    $email = $_POST['email'];

    if ($database->isUserExists($pdo, $email)) {
        $response["error"] = FALSE;
        echo json_encode($response);

    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "User not exists";
        echo json_encode($response);
    }

//    $database->closeConnection();
//    $database->tracker();

}

<?php
/**
 * Created by PhpStorm.
 * User: nikhilkamath
 * Date: 19/06/18
 * Time: 3:00 PM
 */

require_once 'Database_Queries.php';
require_once 'config.php';
require_once 'error_logger.php';


$response = array("error" => FALSE);

if (isset($_POST['email']) && isset($_POST['password'])) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    if (isUserExists($pdo, $email)) {
        openConnection();
        $row = loginUser($pdo, $email, $password);
        if (!$row) {
            $response["error"] = TRUE;
            $response["error_msg"] = "Oops! Something went wrong";
            echo json_encode($response);

        } else {
            $response["error"] = FALSE;
            $response["username"] = $row['username'];
            $response["email"] = $row['email_address'];
            echo json_encode($response);
        }

    } else {
        $response["error"] = TRUE;
        $response["error_msg"] = "You are not a registered user";
        echo json_encode($response);
    }

    closeConnection();
    tracker();

}