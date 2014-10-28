
package com.cyou.mobopick.util;

import android.content.Context;
import android.content.res.Resources;

public class ResUtil
{
    private static ResUtil ru;
    private String pkg;
    private Resources resources;

    private ResUtil(Context paramContext)
    {
        this.pkg = paramContext.getPackageName();
        this.resources = paramContext.getResources();
    }

    public static ResUtil getInstance(Context paramContext)
    {
        if (ru == null)
            ru = new ResUtil(paramContext);
        return ru;
    }

    public int animId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "anim", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int arrayId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "array", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int colorId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "color", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int dimenId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "dimen", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int drawableId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "drawable", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int layoutId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "layout", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int menuId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "menu", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int rawId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "raw", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    protected int resourcesId(Context paramContext, String paramString1, String paramString2)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString2, paramString1, this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int stringId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "string", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int styleId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "style", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }

    public int viewId(String paramString)
    {
        try
        {
            int i = this.resources.getIdentifier(paramString, "id", this.pkg);
            return i;
        } catch (Exception localException)
        {
            localException.printStackTrace();
        }
        return 0;
    }
}
