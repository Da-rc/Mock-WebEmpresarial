import os
import json
import boto3


SSM = boto3.client('ssm')
KMS = boto3.client("kms")
SECRET = boto3.client('secretsmanager')
API_GATEWAY = boto3.client('apigateway')
SECRET_NAME = os.environ.get("SECRET_NAME")
KMS_PARAM = os.environ.get("KMS_PARAM")
api_name = os.environ.get("API_NAME")
stage_name = os.environ.get("STAGE_NAME")

def handler(event, context):
    """Jam Challenge"""
     # Get the REST API ID first
    apis = API_GATEWAY.get_rest_apis()
    api_id = None

     # Find the API ID by name
    for api in apis['items']:
        if api['name'] == api_name:
            api_id = api['id']
            break

    if api_id:
        # Construct the invoke URL
        region = os.environ.get('AWS_REGION', API_GATEWAY.meta.region_name)
        invoke_url = f"https://{api_id}.execute-api.{region}.amazonaws.com/{stage_name}"


        key_parameter = SSM.get_parameter(
            Name=KMS_PARAM,
            WithDecryption=True
        )
        key_id = key_parameter["Parameter"]["Value"]
        # get secret value
        get_secret = SECRET.get_secret_value(SecretId=SECRET_NAME)
        # parse secret value
        secret_value = json.loads(get_secret['SecretString'])
        # do something with the secret value
        secret_text = secret_value.get("password")
        encrypted_data = KMS.encrypt(KeyId=key_id, Plaintext=secret_text.encode())["CiphertextBlob"]

        print(f"Encrypted data: {encrypted_data}")

        decrypted_value = KMS.decrypt(CiphertextBlob=encrypted_data)["Plaintext"]
        html_part_1 = """
        <html>
            <head>
                <style>
                    body {
                        background-color: #f2f2f2;
                    }
                    h1 {
                        color: #007bff;
                        font-size: 2rem;
                    }
                    p {
                        color: #6c757d;
                        font-size: 1.5rem;
                    }
                </style>
            </head>
            <body>
                <center><h1>Welcome to JAM Challenge</h1></center>
                <center><p>Thank you for your support!.</p></center>
                <center><p>API Invoke URL: """ + invoke_url + """</p></center>
        """
        html_part_2 = f"""
                <center><p>Here is your secret: {decrypted_value.decode()}</p></center>
            </body>
        </html>
        """
        html = html_part_1 + html_part_2
        response = {
            "statusCode": 200,
            "headers": {
                "Content-Type": "text/html"
            },
            "body": html
        }
        return response
    else:
        return {
            "statusCode": 404,
            "headers": {
                "Content-Type": "text/html"
            },
            "body": "<html><body><h1>API not found</h1></body></html>"
        }