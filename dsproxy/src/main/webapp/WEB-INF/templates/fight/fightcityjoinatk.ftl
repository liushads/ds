<#include "/include/header.ftl">
<card title="${game_title}">
<p>
${location}<br/>
<@a href="/p?fc_T/${pid}/${random}"/>刷新</a><br/>
行政官：你已经参与攻城，
距离战斗开始时间还有${fight_left_time}秒，
攻方${fight_atk_num}人，守方${fight_def_num}人.
在此期间你离开本城,将无法参与战斗<br/>
<@gofacility/>
</p>
</card>
</wml>
