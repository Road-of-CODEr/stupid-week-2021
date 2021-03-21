/**
 * @param {string} s
 * @return {boolean}
 */
 const checkOnesSegment = (s) => s.includes('0') ? s.indexOf('0') > s.lastIndexOf('1') : true;
