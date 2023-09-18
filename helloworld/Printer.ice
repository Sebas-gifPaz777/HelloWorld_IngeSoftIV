module Demo
{
    interface Printer
    {
        void printString(string s);
        string reply();
        string replyTime();
        double latency();
    }
}