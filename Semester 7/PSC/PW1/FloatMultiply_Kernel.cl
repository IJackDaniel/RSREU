__kernel void floatMultiply(
    __global float* A,
    __global float* B,
    __global float* out
)
{
    int num = get_global_id(0);

    out[num] = A[num] * B[num];
}