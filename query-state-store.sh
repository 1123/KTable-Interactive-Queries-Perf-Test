#!/bin/bash

set -u -e

for id in 23e46ff1-390f-421f-b94c-413f9c39befc 550dc907-136e-4a0b-8953-5db90d6e374a 1398cbe2-6ae5-4635-9532-b49047aa02ab 4661d6f7-5343-4096-a002-459352e317f6 0d11e866-40c3-4b2a-8385-c464153a98f4 19b6d279-22ad-4121-9505-73dfc0c91fbf 1ae6ac3c-3802-4bc6-9e72-92bea5a9981a 7b8ce514-511c-4a1f-a67e-5fc517b563d6 d06da2ff-a9ec-4a41-9d51-5526c5599e6f dbb595db-94c2-4651-9fdf-a170449fa5f6 71f843c7-6d9c-46d0-b3b7-2b0a6b599db2 64c2ece9-906f-4708-ab2b-1abf95fd3e96 1683fac6-3194-4b36-99ed-5cd326ffb63d c0a8cda9-7e73-4bca-b59f-fdaca9e8ef95 2e618afa-3331-4e12-bbbe-639e20c00231 b7b44543-4de9-4b3f-bd8c-dfddaa418b56 ca41cce8-bcd6-48a9-ad6d-abfb84eb1897 a04ad580-18a8-4583-a8ea-cc4ead6c0855 23b0d42d-ad47-4030-9b5b-d81a74e272ef ab81a41b-6175-41f8-aa61-3896b07f362c f9dd785e-c27d-4a5a-beb6-861d855ae1f8 bbee21cf-7df0-4634-9b96-8dd494d627ec 916dd7d5-2441-470e-977e-b0b7ef84e730 92dd74de-8bde-459b-a629-c310339310d3 5fc38f2f-8dcd-4d9a-b3e1-9b4a8b3e739e; do
	echo ""
	echo "Querying instance 1"
	echo ""
	curl localhost:7001/store/$id ||echo could not reach instance 1
	echo ""
	echo "Querying instance 2"
	echo ""
	curl localhost:7002/store/$id ||echo could not reach instance 2
	echo ""
	echo "Querying instance 3"
	echo ""
	curl localhost:7003/store/$id ||echo could not reach instance 3
done
