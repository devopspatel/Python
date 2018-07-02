def count_evens(nums):
    count = 0
    for n in nums:
        if n % 2 == 0:
            count += 1
    return count


def big_diff(nums):
    return (max(nums) - min(nums))


def centered_average(nums):
    return((sum(nums) - max(nums) - min(nums)) / (len(nums) - 2))
