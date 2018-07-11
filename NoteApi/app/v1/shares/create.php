<?php
// Headers
header('Access-Control-Allow-Origin: *');
header('Content-Type: application/json');
header('Access-Control-Allow-Methods: POST');
header('Access-Control-Allow-Headers: Access-Control-Allow-Headers,Content-Type,Access-Control-Allow-Methods, Authorization, X-Requested-With');
include_once '../../includes/ConnectDb.php';
include_once '../../includes/Share.php';
$database = new ConnectDb();
$db = $database->connect();
//$note = new Notes($db);
$share = new Share($db);
$data = json_decode(file_get_contents("php://input"));

$share->share_from = $data->share_from;
$share->share_to = $data->share_to;
$share->note_id = $data->note_id;
$share->shared_at = $data->shared_at;

if($share->create()) {
    echo json_encode(
        array('message' => 'New Share Created')
    );
} else {
    echo json_encode(
        array('message' => 'Share Not Created')
    );
}