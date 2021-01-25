
class Solution(object):
    def countGoodRectangles(self, rectangles):
        return [min(i) for i in rectangles].count(max([min(i) for i in rectangles]))