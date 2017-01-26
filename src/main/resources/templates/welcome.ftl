<!DOCTYPE html>

<html>
<head>
    <title>Demo: Explicit load of a embedded post</title>
    <script type="text/javascript" src="jquery.min.js"></script>
    <link href="css/nanogallery.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="jquery.nanogallery.min.js"></script>
</head>
<body>
<div id="nanoGallery">

</div>
<script>

    var contentGalleryMLN=[
    <#list albums as album>
        {
            srct: '${album.thumnailUrl}',
            title: '${album.name}',
            ID:999${album?index},
            kind:'album'
        },
        <#list album.images as image>
            {
                srct: '${image.thumnailUrl}',
                destURL: '/open/${image.name}',
                ID:1${album?index}${image?index},
                albumID:999${album?index}
            },
        </#list>
    </#list>

        ];

    $(document).ready(function () {
        $("#nanoGallery").nanoGallery({
            thumbnailWidth:300,
            thumbnailHeight:300,
            //paginationMaxItemsPerPage:3,
            paginationMaxLinesPerPage:2,
//        paginationDots : true,
            paginationVisiblePages: 10,
            thumbnailHoverEffect:'imageScale150',
            viewerDisplayLogo:false,
            useTags:false,
            breadcrumbAutoHideTopLevel:true,
            maxItemsPerLine:5,
            locationHash: false,
            items:contentGalleryMLN
        });
    });
</script>
</body>
</html>