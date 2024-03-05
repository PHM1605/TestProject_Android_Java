package top.mintek.testproject_android_java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CrazyEightView extends View {
  private int scrW;
  private int scrH;
  private int scaledCW;
  private int scaledCH;
  private float scale;

  private Context ctx;
  private List<Card> deck = new ArrayList<>();
  private List<Card> playerHand = new ArrayList<>();
  private List<Card> computerHand = new ArrayList<>();
  private List<Card> discardPile = new ArrayList<>();
  private Bitmap cardBack;
  private Paint paint;

  private boolean myTurn;

  public CrazyEightView(Context context) {
    super(context);
    ctx = context;
    // Current device may have more pixels per inch (e.g. 2x more) than standard
    scale = ctx.getResources().getDisplayMetrics().density;
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    paint.setStyle(Paint.Style.FILL);
    paint.setTextAlign(Paint.Align.LEFT);
    paint.setTextSize(scale * 15);
  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    scrW = w;
    scrH = h;
    scaledCW = (int)(scrW/8);
    scaledCH = (int)(scaledCW * 1.28);
    initializeDeck();
    dealCards();
    Bitmap tempBitmap = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.card_back);
    cardBack = Bitmap.createScaledBitmap(tempBitmap, scaledCW, scaledCH, false);
    myTurn = new Random().nextBoolean();
    if(!myTurn) {
      computerPlay();
    }
  }

  protected void onDraw(@NonNull Canvas canvas) {
    // Draw computer deck
    canvas.drawText("Opponent Score: ", 10, paint.getTextSize()+10, paint);
    for(int i=0; i<computerHand.size(); i++) {
      canvas.drawBitmap(cardBack, i*(scale*5), paint.getTextSize()+(50*scale), null);
    }
    // Draw hand deck
    for(int i=0; i<playerHand.size(); i++) {
      canvas.drawBitmap(playerHand.get(i).getBitmap(), i*(scaledCW+5), scrH - scaledCH - paint.getTextSize()-(50*scale), null);
    }
    // Draw pile (shape of the back of a card)
    int cbackLeft = (scrW/2) - cardBack.getWidth() - 10;
    int cbackTop = (scrH/2) - cardBack.getHeight()/2;
    canvas.drawBitmap(cardBack, cbackLeft, cbackTop, null);
    // Discard pile
    if(!discardPile.isEmpty()) {
      canvas.drawBitmap(discardPile.get(0).getBitmap(), (scrW/2)+10, (scrH/2)-cardBack.getHeight()/2, null);
    }

    invalidate();
  }

  public boolean onTouchEvent(MotionEvent event) {
    int eventaction = event.getAction();
    int X = (int)event.getX();
    int Y = (int)event.getY();
    switch (eventaction) {
      cas
    }
  }

  private void initializeDeck() {
    for(int i=0; i<4; i++) {
      for(int j=102; j<115; j++) {
        int tempId = j + (i*100);
        Card tempCard = new Card(tempId);
        @SuppressLint("DiscouragedApi")
        int resourceId = getResources().getIdentifier("card" + tempId, "drawable", ctx.getPackageName());
        Bitmap tempBitmap = BitmapFactory.decodeResource(ctx.getResources(), resourceId);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap, scaledCW, scaledCH, false);
        tempCard.setBitmap(scaledBitmap);
        deck.add(tempCard);
      }
    }
  }

  private void drawCard(List<Card> hand) {
    hand.add(0, deck.get(0));
    deck.remove(0);
    // Add discarded cards back to the deck then shuffle
    if (deck.isEmpty()) {
      for (int i = discardPile.size()-1; i>0; i--) {
        deck.add(discardPile.get(i));
        discardPile.remove(i);
        Collections.shuffle(deck, new Random());
      }
    }
  }

  private void dealCards() {
    Collections.shuffle(deck, new Random());
    for(int i=0; i<7; i++) {
      drawCard(playerHand);
      drawCard(computerHand);
    }
  }

  private void computerPlay() {
    int tempPlay = 0;
    while(tempPlay == 0) {
      tempPlay = computerPlayer.playCard(computerHand, validSuit, validRank);
      if(tempPlay == 0) {
        drawCard(computerHand);
      }
    }
  }
}
