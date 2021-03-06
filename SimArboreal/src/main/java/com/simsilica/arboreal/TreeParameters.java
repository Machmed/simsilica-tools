/*
 * $Id$
 *
 * Copyright (c) 2014, Simsilica, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions 
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS 
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT 
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS 
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED 
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.simsilica.arboreal;

import com.simsilica.arboreal.LevelOfDetailParameters.ReductionType;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 *  @author    Paul Speed
 */
public class TreeParameters implements Iterable<BranchParameters> {

    private static final String VERSION_KEY = "formatVersion";
    private static final int VERSION = 1;
    private static final String BRANCHES_KEY = "branches";
    private static final String ROOTS_KEY = "roots";
    private static final String LODS_KEY = "lodLevels";

    private BranchParameters[] branches;
    private BranchParameters[] roots;
    private LevelOfDetailParameters[] lodLevels;
    private float baseScale = 1;
    private float trunkRadius = 0.5f * 0.3f;
    private float trunkHeight = 6 * 0.3f;
    private float rootHeight = 1 * 0.3f;
    private float yOffset = 1 * 0.3f;
    private float flexHeight = 2.0f;
    private float trunkFlexibility = 1.0f;
    private float branchFlexibility = 1.0f;
    private int uRepeat = 4;
    private float vScale = 0.45f;
    private float leafScale = 1;
    private boolean generateLeaves = false; 
    private int seed = 0;
    
    public TreeParameters() {
        this(4);
    }
    
    public TreeParameters( int depth ) {
        this.branches = new BranchParameters[depth];
        for( int i = 0; i < depth; i++ ) {
            branches[i] = new BranchParameters();
            
            // for testing
            //branches[i].enabled = false;
            
            // For any branch greater than depth 3, we disable it by default
            if( i > 3 ) {
                branches[i].enabled = false;
            }            
        }
        this.branches[0].inherit = false;                    
        //this.branches[0].enabled = true;                    
        //this.branches[1].enabled = true;
                            
        this.roots = new BranchParameters[depth];
        for( int i = 0; i < depth; i++ ) {
            roots[i] = new BranchParameters();
            roots[i].enabled = false;
        }
        this.roots[0].inherit = false;
        
        // Roots get a specific default setup
        roots[0].lengthSegments = 1;
        roots[0].segmentVariation = 0;
        roots[0].taper = 1;
        roots[0].sideJointCount = 5;
        roots[0].inclination = 0.5f;
        roots[0].lengthScale = 1.1f;
        roots[0].enabled = true;                    
        
        roots[1].inherit = false;
        roots[1].taper = 0.5f;
        roots[1].gravity = 0.1f;
        roots[1].lengthScale = 1.0f;
        roots[1].enabled = true;
        
        roots[2].enabled = true;                    
 
 
        this.lodLevels = new LevelOfDetailParameters[4];
        for( int i = 0; i < lodLevels.length; i++ ) {
            lodLevels[i] = new LevelOfDetailParameters();
            lodLevels[i].distance = (i + 1) * 20;
            lodLevels[i].branchDepth = 2;
            lodLevels[i].rootDepth = 2;
            lodLevels[i].maxRadialSegments = 3;
            if( i > 0 ) {
                lodLevels[i].reduction = ReductionType.FlatPoly;
            }                           
        }
        lodLevels[0].maxRadialSegments = Integer.MAX_VALUE;                                   
        lodLevels[0].branchDepth = depth;
        lodLevels[0].rootDepth = depth;
        lodLevels[1].maxRadialSegments = 4;
        lodLevels[lodLevels.length-1].distance = Float.MAX_VALUE;                                   
    }
 
    public void setSeed( int seed ) {
        this.seed = seed;
    }
    
    public int getSeed() {
        return seed;
    }
 
    public void setGenerateLeaves( boolean b ) {
        this.generateLeaves = b;
    }
    
    public boolean getGenerateLeaves() {
        return generateLeaves;
    }
 
    public void setBaseScale( float f ) {
        this.baseScale = f;
    }
 
    public float getBaseScale() {
        return baseScale;
    }
 
    public void setTrunkRadius( float f ) {
        this.trunkRadius = f;
    }
    
    public float getTrunkRadius() {
        return trunkRadius;
    } 

    public void setTrunkHeight( float f ) {
        this.trunkHeight = f;
    }
    
    public float getTrunkHeight() {
        return trunkHeight;
    } 
 
    public void setRootHeight( float f ) {
        this.rootHeight = f;
    }
    
    public float getRootHeight() {
        return rootHeight;
    } 
    
    public void setYOffset( float f ) {
        this.yOffset = f;
    }
    
    public float getYOffset() {
        return yOffset;
    }
 
    public void setLeafScale( float f ) {
        this.leafScale = f;
    }
    
    public float getLeafScale() {
        return leafScale;
    }
 
    public void setTextureURepeat( int repeat ) {
        this.uRepeat = repeat;
    }
    
    public int getTextureURepeat() {
        return uRepeat;
    }
    
    public void setTextureVScale( float f ) {
        this.vScale = f;
    }
    
    public float getTextureVScale() {
        return vScale;
    }
 
    public void setFlexHeight( float f ) {
        this.flexHeight = f;
    }
 
    public float getFlexHeight() {
        return flexHeight;
    }
    
    public void setTrunkFlexibility( float f ) {
        this.trunkFlexibility = f;
    }
    
    public float getTrunkFlexibility() {
        return trunkFlexibility;
    }
    
    public void setBranchFlexibility( float f ) {
        this.branchFlexibility = f;
    }
    
    public float getBranchFlexibility() {
        return branchFlexibility;
    }
 
    public int getDepth() {
        return branches.length;
    }
    
    public BranchParameters getBranch( int index ) {
        return branches[index];
    }
    
    public List<BranchParameters> getEffectiveBranches() {
        List<BranchParameters> list = new ArrayList<BranchParameters>();
        for( BranchParameters p : this ) {
            list.add(p);
        }
        return list;
    }
    
    @Override
    public Iterator<BranchParameters> iterator() {
        return new BranchIterator();
    }

    public BranchParameters getRoot( int index ) {
        return roots[index];
    }
    
    public List<BranchParameters> getEffectiveRoots() {
        List<BranchParameters> list = new ArrayList<BranchParameters>();
        for( Iterator<BranchParameters> it = rootIterator(); it.hasNext(); ) {
            BranchParameters p = it.next();
            list.add(p);
        }
        return list;
    }
    
    public Iterator<BranchParameters> rootIterator() {
        return new RootIterator();
    }
 
    public int getLodCount() {
        return lodLevels.length; 
    }
    
    public LevelOfDetailParameters getLod( int i ) {
        return lodLevels[i];
    }
    
    public List<LevelOfDetailParameters> getLods() {
        return Arrays.asList(lodLevels);       
    }
 
    private PropertyDescriptor findProperty( BeanInfo info, String name ) {
        for( PropertyDescriptor pd : info.getPropertyDescriptors() ) {
            if( name.equals(pd.getName()) ) {
                return pd;
            }
        }
        return null;
    }
 
    private BranchParameters[] listToBranches( List<Map<String, Object>> list, BranchParameters[] result ) {
        if( result.length != list.size() ) {
            BranchParameters[] newArray = new BranchParameters[list.size()];
            for( int i = 0; i < newArray.length; i++ ) {
                if( i < result.length ) {
                    newArray[i] = result[i];
                }
            }
        }
        for( int i = 0; i < result.length; i++ ) {
            Map<String, Object> value = list.get(i);
            if( result[i] == null ) {
                result[i] = new BranchParameters();
            }
            result[i].fromMap(value);
        }
        return result;
    }

    private LevelOfDetailParameters[] listToLods( List<Map<String, Object>> list, LevelOfDetailParameters[] result ) {
        if( result.length != list.size() ) {
            LevelOfDetailParameters[] newArray = new LevelOfDetailParameters[list.size()];
            for( int i = 0; i < newArray.length; i++ ) {
                if( i < result.length ) {
                    newArray[i] = result[i];
                }
            }
        }
        for( int i = 0; i < result.length; i++ ) {
            Map<String, Object> value = list.get(i);
            if( result[i] == null ) {
                result[i] = new LevelOfDetailParameters();
            }
            result[i].fromMap(value);
        }
        return result;
    }
 
    public void fromMap( Map<String, Object> map ) {
        Number version = (Number)map.get(VERSION_KEY);
 
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(getClass());
        } catch( Exception e ) {
            throw new RuntimeException("Error introspecting property descriptors", e);
        }
        
        for( Map.Entry<String, Object> e : map.entrySet() ) {
            if( VERSION_KEY.equals(e.getKey()) ) {
                continue;
            }
            if( BRANCHES_KEY.equals(e.getKey()) ) {
                branches = listToBranches((List<Map<String, Object>>)e.getValue(), branches);
            } else if( ROOTS_KEY.equals(e.getKey()) ) {
                roots = listToBranches((List<Map<String, Object>>)e.getValue(), roots);
            } else if( LODS_KEY.equals(e.getKey()) ) {
                lodLevels = listToLods((List<Map<String, Object>>)e.getValue(), lodLevels);
            } else {                
                PropertyDescriptor pd = findProperty(info, e.getKey());
                Method m = pd.getWriteMethod();                
                try {
                    if( pd.getPropertyType() == Integer.TYPE ) {
                        Number value = (Number)e.getValue();
                        m.invoke(this, value.intValue());
                    } else if( pd.getPropertyType() == Float.TYPE ) {
                        Number value = (Number)e.getValue();
                        m.invoke(this, value.floatValue());
                    } else if( pd.getPropertyType() == Boolean.TYPE ) {
                        m.invoke(this, e.getValue());
                    } else {
                        throw new RuntimeException("Unhandled property type:" + pd.getPropertyType());
                    }
                } catch( Exception ex ) {
                    throw new RuntimeException("Error inspecting property:" + pd.getName(), ex);
                } 
            } 
        }
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> result = new TreeMap<String, Object>();
        result.put(VERSION_KEY, VERSION);
 
        PropertyDescriptor[] props = null;
        try {
            BeanInfo info = Introspector.getBeanInfo(getClass());
            props = info.getPropertyDescriptors();
        } catch( Exception e ) {
            throw new RuntimeException("Error introspecting property descriptors", e);
        }
  
        for( PropertyDescriptor pd : props ) {
            if( pd.getWriteMethod() == null ) {
                continue;
            }
            Method m = pd.getReadMethod();
            if( m == null ) {
                continue;
            }
            try {
                result.put(pd.getName(), m.invoke(this));
            } catch( Exception e ) {
                throw new RuntimeException("Error inspecting property:" + pd.getName(), e);
            } 
        }
 
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(branches.length);
        for( BranchParameters bp : branches ) {
            list.add(bp.toMap());   
        }
        result.put(BRANCHES_KEY, list);

        list = new ArrayList<Map<String, Object>>(roots.length);
        for( BranchParameters bp : roots ) {
            list.add(bp.toMap());   
        }
        result.put(ROOTS_KEY, list);

        list = new ArrayList<Map<String, Object>>(lodLevels.length);
        for( LevelOfDetailParameters lod : lodLevels ) {
            list.add(lod.toMap());   
        }
        result.put(LODS_KEY, list);
        
        return result;
    } 
 
    public static void main( String... args ) {
        
        TreeParameters test = new TreeParameters();
        
        Map<String, Object> map = test.toMap();
        System.out.println( "Map:" + map );

        test.baseScale = 0;
        test.trunkRadius = 0;
        test.trunkHeight = 0;
        test.rootHeight = 0;
        test.uRepeat = 0;
        test.vScale = 0;
        test.leafScale = 0;
        test.seed = 0;
        test.branches = new BranchParameters[0];
        test.roots = new BranchParameters[0];

        Map<String, Object> map2 = test.toMap();
        System.out.println( "Map2:" + map2 );

        test.fromMap(map);
        
        Map<String, Object> map3 = test.toMap();
        System.out.println( "Map3:" + map3 );
        
    }
    
    private class BranchIterator implements Iterator<BranchParameters> {
 
        private BranchParameters last;
        private int next = 0;
           
        public BranchIterator() {
            this.last = getBranch(0); 
        }
        
        @Override
        public boolean hasNext() {
            return next < getDepth() && getBranch(next).enabled;
        }
        
        @Override
        public BranchParameters next() {
            BranchParameters result = getBranch(next++);
            if( !result.enabled ) {
                throw new NoSuchElementException();
            }
            if( result.inherit ) {
                result = last;
            } else {
                last = result;
            }
            return result;           
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove branch parameters.");
        }
    }
    
    private class RootIterator implements Iterator<BranchParameters> {
 
        private BranchParameters last;
        private int next = 0;
           
        public RootIterator() {
            this.last = getRoot(0); 
        }
        
        @Override
        public boolean hasNext() {
            return next < getDepth() && getRoot(next).enabled;
        }
        
        @Override
        public BranchParameters next() {
            BranchParameters result = getRoot(next++);
            if( !result.enabled ) {
                throw new NoSuchElementException();
            }
            if( result.inherit ) {
                result = last;
            } else {
                last = result;
            }
            return result;           
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove branch parameters.");
        }
    }
}


