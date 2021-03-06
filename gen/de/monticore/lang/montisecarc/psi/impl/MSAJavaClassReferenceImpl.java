// This is a generated file. Not intended for manual editing.
package de.monticore.lang.montisecarc.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static de.monticore.lang.montisecarc.psi.MSACompositeElementTypes.*;
import de.monticore.lang.montisecarc.psi.*;
import com.intellij.psi.PsiReference;
import static de.monticore.lang.montisecarc.psi.MSATokenElementTypes.*;

public class MSAJavaClassReferenceImpl extends MSACompositeElementImpl implements MSAJavaClassReference {

  public MSAJavaClassReferenceImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull MSAVisitor visitor) {
    visitor.visitJavaClassReference(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof MSAVisitor) accept((MSAVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<MSAJavaReference> getJavaReferenceList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, MSAJavaReference.class);
  }

  @NotNull
  public PsiReference[] getReferences() {
    return MSAPsiImplUtil.getReferences(this);
  }

}
