class Solution(object):
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        
        ind_list = []
        
        for i in range(len(nums)-1):
            temp = target - nums[i]
            
            if (temp in nums[i+1:]):
                ind_list.append(i)
                ind_list.append(nums.index(temp,i+1))
                break
        
        return ind_list
    
    
b = Solution()

li = [2, 7, 11, 15]
tar = 9

li = [1,2,3,4,5,6,7,8,8,7,20,21,22,23,24,25,26,9]
tar = 16
# 
li = [3, 3]
tar = 6

ret = b.twoSum(li, tar)

print (ret)