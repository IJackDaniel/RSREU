__constant sampler_t sampler =
    CLK_NORMALIZED_COORDS_FALSE |
    CLK_ADDRESS_CLAMP |
    CLK_FILTER_NEAREST;

__kernel void task2(
    read_only image2d_t src_image,
    write_only image2d_t dst_image)
{
    uint x = get_global_id(0);
    uint y = get_global_id(1);

    uint height = get_global_size(1);

    int2 src_coord = (int2)(x, height - 1 - y);
    int2 dst_coord = (int2)(x, y);

    uint4 pixel = read_imageui(src_image, sampler, src_coord);

    pixel = (uint4)(255) - pixel;

    write_imageui(dst_image, dst_coord, pixel);
}