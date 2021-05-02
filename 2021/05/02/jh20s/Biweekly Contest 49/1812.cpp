class Solution {
public:
bool squareIsWhite(string coordinates) {
	int x = coordinates[0] - 'a', y = coordinates[1] - '0';
	if (x % 2) {
		return y % 2 ? true : false;
	}
	else {
		return y % 2 ? false :true;
	}
}
};