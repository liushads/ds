<#include "/include/header.ftl">
<card title="${game_title}"><p>
闭关修炼<br/>
<@a href="/p?p_of/${pid}/"/>刷新</a><br/>
你当前累积闭关修炼${hour}小时${minutes}分钟<br/>
【小提示】5小时后会自动停止修炼<br/>
5小时后请尽快上线领走你的收益，方便开始更多的闭关修炼<br/>
<@a href="/p?p_of/${pid}/my/"/>闭关修炼 </a> <@a href="/p?p_of/${pid}/gt/"/>领取经验</a><br/>
您当前可以领取的闭关收益：<br/>
人物经验：${offlineExp} 宠物经验：${offlineExp}<br/>
离线经验池：${totalExp}<br/>
温馨提示：消耗一个闭关修炼符即可领取更多收益<br/>
<@a href="/p?ma_I/${pid}/1/"/>购买闭关修炼符</a><br/>
<@goback/>
<@gogame/>
</p></card>
</wml>