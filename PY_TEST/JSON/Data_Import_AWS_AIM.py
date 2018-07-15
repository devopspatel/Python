import json

x1 = '''{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "s3:Get*",
                "s3:List*"
            ],
            "Resource": "*"
        }
    ]
}'''

d = json.loads(x1)
print(x1)
print(d)
print(d['Version'])
print(d['Statement'])
print(d['Statement'][0])
print(d['Statement'][0]['Effect'])
print(d['Statement'][0]['Action'])
print(d['Statement'][0]['Action'][0])
print(d['Statement'][0]['Action'][1])