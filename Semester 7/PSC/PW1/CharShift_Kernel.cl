__kernel void charShift(
    __global char* in,
    __global char* out,
    char K
)
{
    int num = get_global_id(0);
    out[num] = in[num] + K;
}