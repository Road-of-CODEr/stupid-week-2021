/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 * Definition for singly-linked list.
 * function ListNode(val, next) {
 *     this.val = (val===undefined ? 0 : val)
 *     this.next = (next===undefined ? null : next)
 * }
 */
/**
 * @param {ListNode[]} lists
 * @return {ListNode}
 */
var mergeKLists = function (lists) {
  const merge = function (list1, list2) {
    let sorted = new ListNode();
    const result = sorted;
    while (list1 && list2) {
      if (list1.val < list2.val) {
        const node = new ListNode(list1.val, null);
        sorted.next = node;
        list1 = list1.next;
      } else {
        const node = new ListNode(list2.val, null);
        sorted.next = node;
        list2 = list2.next;
      }
      sorted = sorted.next;
    }
    sorted.next = list1 ? list1 : list2;
    return result.next;
  }

  // mergeKLists()
  if (lists.length === 0) return null;
  if (lists.length === 1) return lists[0]

  let result = merge(lists[0], lists[1]);
  for (let i = 2; i < lists.length; i++) {
    result = merge(result, lists[i])
  }

  return result;
};