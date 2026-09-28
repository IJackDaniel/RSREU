__constant sampler_t sampler =
    CLK_NORMALIZED_COORDS_FALSE |
    CLK_ADDRESS_CLAMP |
    CLK_FILTER_NEAREST;

__kernel void task8(
    read_only image2d_t src_image,
    write_only image2d_t dst_image)
{
    uint x = get_global_id(0);
    uint y = get_global_id(1);

    int2 coord = (int2)(x, y);

    uint4 pixel = read_imageui(src_image, sampler, coord);

    if (x <= 50 && y <= 50)
    {
        pixel = (uint4)(255, 255, 255, 255);
    }
    else
    {
        uint gray = (uint)(
            0.299f * pixel.z +
            0.587f * pixel.y +
            0.114f * pixel.x
        );

        gray = 255 - gray;

        pixel.x = gray;
        pixel.y = gray;
        pixel.z = gray;
    }

    write_imageui(dst_image, coord, pixel);
}