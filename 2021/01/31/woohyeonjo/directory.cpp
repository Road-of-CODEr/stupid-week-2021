#define NAME_MAXLEN 7
#define PATH_MAXCHILD 31
#define MAX_DIRETORY 50001
 
// The below commented functions are for your reference. If you want 
// to use it, uncomment these functions.
 
int mstrcmp(const char *a, const char *b)
{
    int i;
    for (i = 0; a[i] != '\0'; i++)
    {
        if (a[i] != b[i])
            return a[i] - b[i];
    }
    return a[i] - b[i];
}
 
int mstrncmp(const char *a, const char *b, int len)
{
    for (int i = 0; i < len; i++)
    {
        if (a[i] != b[i])
            return a[i] - b[i];
    }
    return 0;
}
 
int mstrlen(const char *a)
{
    int len = 0;
 
    while (a[len] != '\0')
        len++;
 
    return len;
}
 
void mstrcpy(char *dest, const char *src)
{
    int i = 0;
    while (src[i] != '\0')
    {
        dest[i] = src[i];
        i++;
    }
    dest[i] = src[i];
}
 
void mstrncpy(char *dest, const char *src, int len)
{
    for (int i = 0; i<len; i++)
    {
        dest[i] = src[i];
    }
    dest[len] = '\0';
}
 
struct TreeNode
{
    char name[NAME_MAXLEN];
    int childNum;
    int totalNum;
 
    TreeNode *parantNode;
    TreeNode *childNode[PATH_MAXCHILD];
 
};
 
TreeNode Dir[MAX_DIRETORY];
int numOfTree;
 
TreeNode* alloc(char name[])
{
    Dir[numOfTree].childNode[0] = 0;
    Dir[numOfTree].parantNode = 0;
 
    Dir[numOfTree].childNum = 0;
    Dir[numOfTree].totalNum = 0;
    mstrcpy(Dir[numOfTree].name, name);
 
    return &Dir[numOfTree++];
 
}
 
void initTree(void)
{
    numOfTree = 0;
    int i;
 
    for (i = 0; i <= MAX_DIRETORY; i++)
    {
        Dir[i].name[0] = 0;
        Dir[i].childNum = 0;
 
        Dir[i].parantNode = 0;
 
        for (register int j = 0; j < PATH_MAXCHILD; j++)
        {
 
            Dir[i].childNode[j] = 0;
        }
    }
 
}
 
 
void addChild(TreeNode* p, TreeNode* c)
{
    for (register int i = 0; i <= PATH_MAXCHILD; i++)
    {
        if (!p->childNode[i])
        {
            p->childNode[i] = c;
            break;
        }
    }
    p->childNum++;
    p->totalNum++;
    c->parantNode = p;
 
    while (p->parantNode != 0)
    {
        p->parantNode->totalNum++;
 
        p = p->parantNode;
    }
}
 
 
void preOrder(TreeNode* s)
{
    TreeNode* child;
    int num = s->childNum;
 
    for (register int i = 0; i < num; i++)
    {
        child = s->childNode[i];
 
        if (child != 0)
        {
            preOrder(child);
        }
    }
}
 
int _root;
int _sizeOfDir;
char pathWord[PATH_MAXCHILD][NAME_MAXLEN];
 
void init(int n) 
{
    _root = 0;
    _sizeOfDir = n;
 
    initTree();
 
    char root[NAME_MAXLEN] = { '/', };
    alloc(root);
 
    for (register int i = 0; i <= PATH_MAXCHILD; i++)
    {
        for (register int j = 0; j < NAME_MAXLEN; j++)
        {
            pathWord[i][j] = 0;
        }
    }
 
}
void parsingPath(char src[], char dst[PATH_MAXCHILD][NAME_MAXLEN], int* num)
{
    *num = 0;
    register int i = 1;
    register int j = 0;
    while (src[i] != '\0')
    {
        if (src[i] == '/')
        {
            *num = *num + 1;
            i++;
            j = 0;
            continue;
        }
 
        dst[*num][j] = src[i];
        i++;
        j++;
    }
         
    return;
}
 
TreeNode* findNode(char path[PATH_MAXCHILD + 1], int *childnum)
{ 
    TreeNode* temp = &Dir[_root];
 
    int number = 0;
    parsingPath(path, pathWord, &number);
    for (register int i = 0; i < number; i++)
    {
        for (register int j = 0; j < PATH_MAXCHILD; j++)
        {
            if (temp->childNode[j])
            {
                if (mstrcmp(pathWord[i], temp->childNode[j]->name) == 0)
                {
                    temp = temp->childNode[j];
                    *childnum = j;
                    break;
                }
            }
        }
    }
 
    for (register int i = 0; i < number; i++)
    {
        for (register int j = 0; j < NAME_MAXLEN; j++)
        {
            pathWord[i][j] = 0;
        }
    }
 
    return temp;
}
 
void cmd_mkdir(char path[PATH_MAXCHILD + 1], char name[NAME_MAXLEN + 1]) 
{
    int num = 0;
    TreeNode* c = alloc(name);
    TreeNode* p = findNode(path, &num);
 
    addChild(p, c);
}
 
void cmd_rm(char path[PATH_MAXCHILD + 1]) 
{
    int num = 0;
    TreeNode* p = findNode(path, &num);
 
    TreeNode* temp = p->parantNode;
    while (temp)
    {
        temp->totalNum -= (p->totalNum + 1);
        temp = temp->parantNode;
    }
 
    p->parantNode->childNode[num] = nullptr;
    p->parantNode->childNum--;
 
    p->parantNode = nullptr;
}
 
void cmd_mv(char srcPath[PATH_MAXCHILD + 1], char dstPath[PATH_MAXCHILD + 1])
{
    int nums = 0;
    TreeNode* s = findNode(srcPath, &nums);
 
    int numd = 0;
    TreeNode* d = findNode(dstPath, &numd);
 
    TreeNode* temp = s->parantNode;
    
    while (temp)
    {
        temp->totalNum -= (s->totalNum+1);
        temp = temp->parantNode;
    }
 
    s->parantNode->childNode[nums] = nullptr;
    s->parantNode->childNum--;
     
    d->totalNum += (s->totalNum);
     
    addChild(d, s);
 
    temp = d->parantNode;
    while (temp)
    {
        temp->totalNum += s->totalNum;
        temp = temp->parantNode;
    }
 
}
 
void nodecopy(TreeNode *s, TreeNode *d)
{
    TreeNode* newNode = alloc(s->name);
    addChild(d, newNode);
 
    d = newNode;
    
    TreeNode* child = nullptr;
    int num = s->childNum;
    int f = 0;
 
    if (num == 0) return;
 
    for (register int i = 0; i < PATH_MAXCHILD; i++)
    {
        if (f == num)
        {
            return;
        }
 
        child = s->childNode[i];
        if (child == 0)
        {
            continue;
        }
        else
        {
            nodecopy(child, d);
            f++;
        }
    }
    return;
}

void cmd_cp(char srcPath[PATH_MAXCHILD + 1], char dstPath[PATH_MAXCHILD + 1]) 
{
    int nums = 0;
    TreeNode* s = findNode(srcPath, &nums);
 
    int numd = 0;
    TreeNode* d = findNode(dstPath, &numd);
 
    nodecopy(s, d);
}
 
int cmd_find(char path[PATH_MAXCHILD + 1]) 
{
    int nums = 0;
    TreeNode* s = findNode(path, &nums);
 
    return s->totalNum;
}
