#pragma OPENCL EXTENSION cl_khr_fp64 : enable

__kernel void calculatePi(
    const ulong numSteps,
    const double step,
    __global double* groupSums,
    __local double* localSums)
{
    const size_t globalId = get_global_id(0);
    const size_t localId = get_local_id(0);

    const size_t globalSize = get_global_size(0);
    const size_t localSize = get_local_size(0);

    double sum = 0.0;

    // Каждый work-item обрабатывает свою часть прямоугольников.
    for (ulong i = globalId; i < numSteps; i += globalSize)
    {
        double x = ((double)i + 0.5) * step;
        sum += 4.0 / (1.0 + x * x);
    }

    // Записываем частичную сумму в общую память work-group.
    localSums[localId] = sum;

    barrier(CLK_LOCAL_MEM_FENCE);

    // Reduction внутри work-group.
    for (size_t offset = localSize / 2; offset > 0; offset /= 2)
    {
        if (localId < offset)
        {
            localSums[localId] += localSums[localId + offset];
        }

        barrier(CLK_LOCAL_MEM_FENCE);
    }

    // После reduction элемент 0 содержит сумму всей группы.
    if (localId == 0)
    {
        const size_t groupId = get_group_id(0);
        groupSums[groupId] = localSums[0];
    }
}