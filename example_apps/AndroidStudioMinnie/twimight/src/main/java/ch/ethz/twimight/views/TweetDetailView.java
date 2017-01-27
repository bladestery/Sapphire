package ch.ethz.twimight.views;

import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.TweetEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import ch.ethz.twimight.R;
import ch.ethz.twimight.activities.LoginActivity;
import ch.ethz.twimight.activities.PhotoViewActivity;
import ch.ethz.twimight.activities.SearchableActivity;
import ch.ethz.twimight.activities.UserProfileActivity;
import ch.ethz.twimight.activities.WebViewActivity;
import ch.ethz.twimight.data.HtmlPagesDbHelper;
import ch.ethz.twimight.net.Html.HtmlPage;
import ch.ethz.twimight.net.twitter.EntityQueue;
import ch.ethz.twimight.net.twitter.Tweets;
import ch.ethz.twimight.net.twitter.TweetsContentProvider;
import ch.ethz.twimight.net.twitter.TwitterSyncService;
import ch.ethz.twimight.net.twitter.TwitterUsers;
import ch.ethz.twimight.util.ImageUrlHelper;
import ch.ethz.twimight.util.SDCardHelper;
import ch.ethz.twimight.util.Serialization;

import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetDetailView extends FrameLayout {

	private static final String TAG = TweetDetailView.class.getSimpleName();

	private final long mRowId;
	private ContentObserver mObserver;
	private Cursor mCursor;
	private Uri mUri;
	private SDCardHelper mSdCardHelper;
	private ConnectivityManager mConnectivityManager;
	private HtmlPagesDbHelper mHtmlDbHelper;

	// Views
	private TextView mTvScreenName;
	private TextView mTvRealName;
	private TextView mTvTweetText;
	private TextView mTvTweetCreationDetails;
	private TextView mTvRetweetedBy;
	private LinearLayout mUnverifiedInfo;
	private LinearLayout mUserInfoView;
	private ImageView mIvProfilePicture;
	private LinearLayout mToSendNotification;
	private LinearLayout mToDeleteNotification;
	private LinearLayout mToFavoriteNotification;
	private LinearLayout mToUnfavoriteNotification;
	private LinearLayout mToRetweetNotification;
	private LinearLayout mImageContainer;
	private TextView mToDeleteText;
	private View mRetweetStatus;
	private TextView mTvRetweetCount;
	private View mFavoriteStatus;
	private TextView mTvFavoriteStatus;

	private int mFlags;
	private int mBuffer;
	private long mUserRowId;
	private String mText;
	private String mScreenName;
	private String mTwitterUserId;
	private String mPhotoDirectoryPath;

	public TweetDetailView(Context context, long rowId) {
		super(context);
		((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.tweet_detail_view, this, true);
		setBackgroundColor(context.getResources().getColor(R.color.white));
		captureViews();
		Log.d(TAG, "views captured " + this);
		mRowId = rowId;
		mUri = Uri.parse("content://" + Tweets.TWEET_AUTHORITY + "/" + Tweets.TWEETS + "/" + mRowId);
		mSdCardHelper = new SDCardHelper();
		mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		mHtmlDbHelper = new HtmlPagesDbHelper(context);
		mHtmlDbHelper.open();
		updateCursor();
	}

	@Override
	protected void onAttachedToWindow() {
		Log.d(TAG, "onAttachedToWindow " + this);
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow() {
		discardCursor();
		super.onDetachedFromWindow();
	}

	private void updateCursor() {
		discardCursor();
		mCursor = getContext().getContentResolver().query(mUri, null, null, null, null);

		if (mCursor != null) {
			Log.d(TAG, "cursor not null");
			mObserver = new TweetObserver(new Handler());
			mCursor.registerContentObserver(mObserver);
			if (mCursor.getCount() > 0) {
				mCursor.moveToFirst();
				updateContent();
			}
		} else {
			Log.d(TAG, "cursor is null");
			mCursor = null; // discard completely so that we don't try to
							// unregister observer from empty cursor
		}
	}

	private void discardCursor() {
		if (mCursor != null) {
			mCursor.unregisterContentObserver(mObserver);
			mCursor.close();
			mCursor = null;
		}
	}

	private void captureViews() {
		mTvScreenName = (TextView) findViewById(R.id.showTweetScreenName);
		mTvRealName = (TextView) findViewById(R.id.showTweetRealName);
		mTvTweetText = (TextView) findViewById(R.id.showTweetText);
		mTvTweetCreationDetails = (TextView) findViewById(R.id.tvTweetCreationDetails);
		mTvRetweetedBy = (TextView) findViewById(R.id.showTweetRetweeted_by);
		mUnverifiedInfo = (LinearLayout) findViewById(R.id.showTweetUnverified);
		mUserInfoView = (LinearLayout) findViewById(R.id.showTweetUserInfo);
		mIvProfilePicture = (ImageView) findViewById(R.id.showTweetProfileImage);
		mToSendNotification = (LinearLayout) findViewById(R.id.showTweetTosend);
		mToDeleteNotification = (LinearLayout) findViewById(R.id.showTweetTodelete);
		mToFavoriteNotification = (LinearLayout) findViewById(R.id.showTweetTofavorite);
		mToUnfavoriteNotification = (LinearLayout) findViewById(R.id.showTweetTounfavorite);
		mToRetweetNotification = (LinearLayout) findViewById(R.id.showTweetToretweet);
		mImageContainer = (LinearLayout) findViewById(R.id.imageContainer);
		mToDeleteText = (TextView) findViewById(R.id.showTweetInfoText2);
		mRetweetStatus = findViewById(R.id.retweetStatus);
		mTvRetweetCount = (TextView) findViewById(R.id.tvRetweetCount);
		mFavoriteStatus = findViewById(R.id.favoriteStatus);
		mTvFavoriteStatus = (TextView) findViewById(R.id.tvFavoriteCount);
	}

	private final class TweetObserver extends ContentObserver {
		public TweetObserver(Handler handler) {
			super(handler);
		}

		@Override
		public boolean deliverSelfNotifications() {
			return true;
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			updateCursor();
		}
	}

	private void updateContent() {
		mBuffer = mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_BUFFER));
		mFlags = mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_FLAGS));
		mTwitterUserId = String.valueOf(mCursor.getLong(mCursor.getColumnIndex(TwitterUsers.COL_TWITTER_USER_ID)));
		mPhotoDirectoryPath = Tweets.PHOTO_PATH + "/" + mTwitterUserId;
		setTweetInfo();
		setUserInfo();
		setProfilePicture();
		setImages();
		mUnverifiedInfo.setVisibility(LinearLayout.GONE);
		if ((mBuffer & Tweets.BUFFER_DISASTER) != 0) {

			if (mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_IS_VERIFIED)) == 0) {
				mUnverifiedInfo.setVisibility(LinearLayout.VISIBLE);
			}
		}
		handleTweetFlags();
		// If there are any flags, schedule the Tweet for synch
		if (mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_FLAGS)) > 0) {
			Intent i = new Intent(getContext(), TwitterSyncService.class);
			i.putExtra(TwitterSyncService.EXTRA_KEY_ACTION, TwitterSyncService.EXTRA_ACTION_SYNC_LOCAL_TWEET);
			i.putExtra(TwitterSyncService.EXTRA_KEY_TWEET_ROW_ID, Long.valueOf(mUri.getLastPathSegment()));
			getContext().startService(i);
		}
	}

	/**
	 * The tweet info
	 * 
	 */
	private void setTweetInfo() {

		mScreenName = mCursor.getString(mCursor.getColumnIndex(TwitterUsers.COL_SCREEN_NAME));
		mTvScreenName.setText("@" + mScreenName);
		mTvRealName.setText(mCursor.getString(mCursor.getColumnIndex(TwitterUsers.COL_NAME)));
		mText = mCursor.getString(mCursor.getColumnIndex(Tweets.COL_TEXT_PLAIN));

		byte[] serializedMentionEntities = mCursor.getBlob(mCursor.getColumnIndex(Tweets.COL_USER_MENTION_ENTITIES));
		UserMentionEntity[] userMentionEntities = Serialization.deserialize(serializedMentionEntities);

		byte[] serializedHashtagEntities = mCursor.getBlob(mCursor.getColumnIndex(Tweets.COL_HASHTAG_ENTITIES));
		HashtagEntity[] hashtagEntities = Serialization.deserialize(serializedHashtagEntities);

		byte[] serializedMediaEntities = mCursor.getBlob(mCursor.getColumnIndex(Tweets.COL_MEDIA_ENTITIES));
		MediaEntity[] mediaEntities = Serialization.deserialize(serializedMediaEntities);

		byte[] serializedUrlEntities = mCursor.getBlob(mCursor.getColumnIndex(Tweets.COL_URL_ENTITIES));
		URLEntity[] urlEntities = Serialization.deserialize(serializedUrlEntities);

		EntityQueue allEntities = new EntityQueue(userMentionEntities, hashtagEntities, mediaEntities, urlEntities);

		SpannableStringBuilder tweetTextSpannable = new SpannableStringBuilder(mText);

		int normalLinkColor = getResources().getColor(R.color.medium_gray);
		int pressedLinkColor = getResources().getColor(R.color.medium_dark_gray);
		int pressedLinkBackground = getResources().getColor(R.color.lighter_gray);

		while (!allEntities.isEmpty()) {
			TweetEntity entity = allEntities.remove();
			if (entity instanceof UserMentionEntity) {
				UserMentionEntity userMentionEntity = (UserMentionEntity) entity;
				tweetTextSpannable.setSpan(new MentionClickableSpan(userMentionEntity.getScreenName(), normalLinkColor,
						pressedLinkColor, pressedLinkBackground), userMentionEntity.getStart(), userMentionEntity
						.getEnd(), Spannable.SPAN_MARK_MARK);

			} else if (entity instanceof HashtagEntity) {
				HashtagEntity hashtagEntity = (HashtagEntity) entity;
				tweetTextSpannable.setSpan(new HashtagClickableSpan(hashtagEntity.getText(), normalLinkColor,
						pressedLinkColor, pressedLinkBackground), hashtagEntity.getStart(), hashtagEntity.getEnd(),
						Spannable.SPAN_MARK_MARK);
			} else if (entity instanceof MediaEntity) {
				// MediaEntities must come before URLEntities because they are a
				// specialized version of URLEntities

				// set the spans in the tweet
				MediaEntity mediaEntity = (MediaEntity) entity;
				tweetTextSpannable.setSpan(new InternalURLSpan(mediaEntity.getURL(), normalLinkColor, pressedLinkColor,
						pressedLinkBackground), mediaEntity.getStart(), mediaEntity.getEnd(), Spannable.SPAN_MARK_MARK);

				tweetTextSpannable.replace(mediaEntity.getStart(), mediaEntity.getEnd(), mediaEntity.getDisplayURL());
				
			} else if (entity instanceof URLEntity) {
				URLEntity urlEntity = (URLEntity) entity;
				tweetTextSpannable.setSpan(new InternalURLSpan(urlEntity.getURL(), normalLinkColor, pressedLinkColor,
						pressedLinkBackground), urlEntity.getStart(), urlEntity.getEnd(), Spannable.SPAN_MARK_MARK);
				tweetTextSpannable.replace(urlEntity.getStart(), urlEntity.getEnd(), urlEntity.getDisplayURL());
			}

		}

		mTvTweetText.setText(tweetTextSpannable);
		mTvTweetText.setMovementMethod(new LinkTouchMovementMethod());

		StringBuffer tweetCreationDetails = new StringBuffer();

		// created at
		tweetCreationDetails.append(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
				.format(new Date(mCursor.getLong(mCursor.getColumnIndex(Tweets.COL_CREATED_AT)))).toString());

		// created via (if available)
		if (mCursor.getString(mCursor.getColumnIndex(Tweets.COL_SOURCE)) != null) {
			tweetCreationDetails.append(getResources().getString(R.string.via));
			tweetCreationDetails.append(Html.fromHtml(mCursor.getString(mCursor.getColumnIndex(Tweets.COL_SOURCE))));
		}
		mTvTweetCreationDetails.setText(tweetCreationDetails);

		// retweeted by
		String retweeted_by = mCursor.getString(mCursor.getColumnIndex(Tweets.COL_RETWEETED_BY));
		if (retweeted_by != null) {
			mTvRetweetedBy.setText(getContext().getString(R.string.retweeted_by) + " " + retweeted_by);
			mTvRetweetedBy.setVisibility(View.VISIBLE);
		} else {
			mTvRetweetedBy.setVisibility(View.GONE);
		}

		// retweet and favorite count
		int retweetCount = mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_RETWEET_COUNT));
		NumberFormat numberFormat = NumberFormat.getInstance();
		if (retweetCount > 0) {
			mRetweetStatus.setVisibility(View.VISIBLE);
			mTvRetweetCount.setText(numberFormat.format(retweetCount));
		} else {
			mRetweetStatus.setVisibility(View.GONE);
		}
		int favoriteCount = mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_FAVORITE_COUNT));
		if (favoriteCount > 0) {
			mFavoriteStatus.setVisibility(View.VISIBLE);
			mTvFavoriteStatus.setText(numberFormat.format(favoriteCount));
		} else {
			mFavoriteStatus.setVisibility(View.GONE);
		}
	}
	
	private void setImages(){
		mImageContainer.removeAllViews();
		String serializedMediaUris = mCursor.getString(mCursor.getColumnIndex(Tweets.COL_MEDIA_URIS));
		for (String mediaUri : ImageUrlHelper.deserializeUrlList(serializedMediaUris)) {
			addImage(mediaUri);
		}
	}

	/**
	 * The user info
	 */
	private void setUserInfo() {
		mUserRowId = mCursor.getLong(mCursor.getColumnIndex(TweetsContentProvider.COL_USER_ROW_ID));
		mUserInfoView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), UserProfileActivity.class);
				i.putExtra(UserProfileActivity.EXTRA_KEY_ROW_ID, mUserRowId);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				getContext().startActivity(i);
			}

		});

	}

	/**
	 * Sets the profile picture.
	 */
	private void setProfilePicture() {
		String profilePictureUrl = mCursor.getString(mCursor.getColumnIndex(TwitterUsers.COL_PROFILE_IMAGE_URI));
		ImageLoader.getInstance().displayImage(profilePictureUrl, mIvProfilePicture);
	}

	/**
	 * Creates an image view, sets it to display the specified source, attaches
	 * a click listener that launches the PhotoViewActivity and adds it to the
	 * image container. 
	 * 
	 * @param imageUri
	 *            remote picture URL of a picture contained in the tweet or
	 *            local file URI
	 */
	private void addImage(String imageUri) {
		Intent imageClickIntent = new Intent(getContext(), PhotoViewActivity.class);
		imageClickIntent.putExtra(PhotoViewActivity.EXTRA_KEY_IMAGE_URI, imageUri);
		ClickableImageView imageView = new ClickableImageView(getContext(), imageUri, imageClickIntent);
		imageView.setScaleType(ScaleType.CENTER_INSIDE);
		imageView.setBackgroundResource(R.drawable.image_placeholder);
		imageView.setAdjustViewBounds(true);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, (int) getContext().getResources().getDimension(R.dimen.unit_step), 0, 0);
		mImageContainer.addView(imageView, layoutParams);
	}

	/**
	 * Set the tweet picture.
	 */
	private void setPhotoAttached() {
		if (!mCursor.isNull(mCursor.getColumnIndex(Tweets.COL_MEDIA_URIS))) {
			String photoFileName = mCursor.getString(mCursor.getColumnIndex(Tweets.COL_MEDIA_URIS));
			Uri photoUri = Uri.fromFile(mSdCardHelper.getFileFromSDCard(mPhotoDirectoryPath, photoFileName));// photoFileParent,
			addImage(photoUri.toString());
		}
	}

	/**
	 * Sets the visibility of the info icons according to the tweet's flags.
	 */
	private void handleTweetFlags() {

		if (mToSendNotification != null) {
			if ((mFlags & Tweets.FLAG_TO_INSERT) == 0) {
				mToSendNotification.setVisibility(LinearLayout.GONE);
			} else {
				mToSendNotification.setVisibility(LinearLayout.VISIBLE);
			}
		}

		if (mToDeleteNotification != null) {
			if ((mFlags & Tweets.FLAG_TO_DELETE) == 0) {
				mToDeleteNotification.setVisibility(LinearLayout.GONE);

			} else {
				mToDeleteNotification.setVisibility(LinearLayout.VISIBLE);

				if (mToDeleteText != null) {
					mToDeleteText.setBackgroundResource(android.R.drawable.list_selector_background);
					mToDeleteText.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mToDeleteNotification != null) {

								int num = getContext().getContentResolver().update(mUri, removeDeleteFlag(mFlags),
										null, null);
								mToDeleteNotification.setVisibility(LinearLayout.GONE);
								if (num > 0) {
									if (mCursor != null) {
										mFlags = mCursor.getInt(mCursor.getColumnIndex(Tweets.COL_FLAGS));
									}
								}
							}
						}
					});
				}
			}
		}

		if (mToFavoriteNotification != null) {
			if ((mFlags & Tweets.FLAG_TO_FAVORITE) == 0) {
				mToFavoriteNotification.setVisibility(LinearLayout.GONE);
			} else {
				mToFavoriteNotification.setVisibility(LinearLayout.VISIBLE);
			}
		}

		if (mToUnfavoriteNotification != null) {
			if ((mFlags & Tweets.FLAG_TO_UNFAVORITE) == 0) {
				mToUnfavoriteNotification.setVisibility(LinearLayout.GONE);
			} else {
				mToUnfavoriteNotification.setVisibility(LinearLayout.VISIBLE);
			}
		}

		if (mToRetweetNotification != null) {
			if ((mFlags & Tweets.FLAG_TO_RETWEET) == 0) {
				mToRetweetNotification.setVisibility(LinearLayout.GONE);
			} else {
				mToRetweetNotification.setVisibility(LinearLayout.VISIBLE);
			}
		}
	}

	/**
	 * Removes the delete flag and returns the flags in a content value
	 * structure to send to the content provider
	 * 
	 * @param flags
	 * @return
	 */
	private ContentValues removeDeleteFlag(int flags) {
		ContentValues cv = new ContentValues();
		cv.put(Tweets.COL_FLAGS, flags & (~Tweets.FLAG_TO_DELETE));
		cv.put(Tweets.COL_BUFFER, mBuffer);
		return cv;
	}

	private class MentionClickableSpan extends TouchableSpan {
		private final String mScreenName;

		public MentionClickableSpan(String screenName, int normalTextColor, int pressedTextColor,
				int pressedBackgroundColor) {
			super(normalTextColor, pressedTextColor, pressedBackgroundColor);
			mScreenName = screenName;
		}

		@Override
		public void onClick(View view) {
			Intent i = new Intent(view.getContext(), UserProfileActivity.class);
			i.putExtra(UserProfileActivity.EXTRA_KEY_SCREEN_NAME, mScreenName);
			view.getContext().startActivity(i);
		}
	}

	/**
	 * A specialized Clickable span that updates its appearance when it is
	 * pressed.
	 * 
	 * @author msteven
	 * 
	 */
	private abstract class TouchableSpan extends ClickableSpan {
		private boolean mIsPressed;
		private int mPressedBackgroundColor;
		private int mNormalTextColor;
		private int mPressedTextColor;

		public TouchableSpan(int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
			mNormalTextColor = normalTextColor;
			mPressedTextColor = pressedTextColor;
			mPressedBackgroundColor = pressedBackgroundColor;
		}

		public void setPressed(boolean isSelected) {
			mIsPressed = isSelected;
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			super.updateDrawState(ds);
			ds.setColor(mIsPressed ? mPressedTextColor : mNormalTextColor);
			ds.bgColor = mIsPressed ? mPressedBackgroundColor : 0xffffffff;
			ds.setUnderlineText(false);
		}
	}

	/**
	 * Interprets touch events on a TextView so that the pressed span can be
	 * highlighted.
	 * 
	 * @author msteven
	 * 
	 */
	private class LinkTouchMovementMethod extends LinkMovementMethod {
		private TouchableSpan mPressedSpan;

		@Override
		public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				mPressedSpan = getPressedSpan(textView, spannable, event);
				if (mPressedSpan != null) {
					mPressedSpan.setPressed(true);
					Selection.setSelection(spannable, spannable.getSpanStart(mPressedSpan),
							spannable.getSpanEnd(mPressedSpan));
				}
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				TouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
				if (mPressedSpan != null && touchedSpan != mPressedSpan) {
					mPressedSpan.setPressed(false);
					mPressedSpan = null;
					Selection.removeSelection(spannable);
				}
			} else {
				if (mPressedSpan != null) {
					mPressedSpan.setPressed(false);
					super.onTouchEvent(textView, spannable, event);
				}
				mPressedSpan = null;
				Selection.removeSelection(spannable);
			}
			return true;
		}

		private TouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {

			int x = (int) event.getX();
			int y = (int) event.getY();

			x -= textView.getTotalPaddingLeft();
			y -= textView.getTotalPaddingTop();

			x += textView.getScrollX();
			y += textView.getScrollY();

			Layout layout = textView.getLayout();
			int line = layout.getLineForVertical(y);
			int off = layout.getOffsetForHorizontal(line, x);

			TouchableSpan[] link = spannable.getSpans(off, off, TouchableSpan.class);
			TouchableSpan touchedSpan = null;
			if (link.length > 0) {
				touchedSpan = link[0];
			}
			return touchedSpan;
		}

	}

	private class HashtagClickableSpan extends TouchableSpan {
		private final String mHashtag;

		public HashtagClickableSpan(String hashtag, int normalTextColor, int pressedTextColor,
				int pressedBackgroundColor) {
			super(normalTextColor, pressedTextColor, pressedBackgroundColor);
			mHashtag = "#" + hashtag;
		}

		@Override
		public void onClick(View view) {
			Intent i = new Intent(view.getContext(), SearchableActivity.class);
			i.putExtra(SearchManager.QUERY, mHashtag);
			view.getContext().startActivity(i);
		}
	}

	private class InternalURLSpan extends TouchableSpan {
		String url;

		public InternalURLSpan(String url, int normalTextColor, int pressedTextColor, int pressedBackgroundColor) {
			super(normalTextColor, pressedTextColor, pressedBackgroundColor);
			this.url = url;
		}

		@Override
		public void onClick(View widget) {

			if (mConnectivityManager.getActiveNetworkInfo() != null
					&& mConnectivityManager.getActiveNetworkInfo().isConnected()) {
				// if there is active internet access, use normal browser
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				getContext().startActivity(intent);
			} else {
				String[] filePath = { HtmlPage.HTML_PATH + "/" + LoginActivity.getTwitterId(getContext()) };
				PackageManager pm;
				Cursor c;

				switch (mSdCardHelper.checkFileType(url)) {

				case SDCardHelper.TYPE_XML:

					Cursor cursorInfo = mHtmlDbHelper.getPageInfo(url);
					if (cursorInfo != null) {

						String filename = null;
						if (!cursorInfo.isNull(cursorInfo.getColumnIndex(HtmlPage.COL_FILENAME))) {
							filename = cursorInfo.getString(cursorInfo.getColumnIndex(HtmlPage.COL_FILENAME));

							if (mSdCardHelper.getFileFromSDCard(filePath[0], filename).length() > 0) {

								// set up our own web view
								Intent intentToWeb = new Intent(getContext(), WebViewActivity.class);
								intentToWeb.putExtra("url", url);
								intentToWeb.putExtra("filename", filename);
								getContext().startActivity(intentToWeb);

							} else {
								Toast.makeText(getContext(), getContext().getString(R.string.faulty_page),
										Toast.LENGTH_LONG).show();
							}

						} else
							Toast.makeText(getContext(), getContext().getString(R.string.file_not_exists),
									Toast.LENGTH_LONG).show();

					} else {
						Log.i(TAG, "content values null");
					}

					break;

				case SDCardHelper.TYPE_PDF:
					Log.i(TAG, "view pdf");
					c = mHtmlDbHelper.getPageInfo(url);
					if (c != null) {
						Intent intentToPDF = new Intent(Intent.ACTION_VIEW, Uri.fromFile(mSdCardHelper
								.getFileFromSDCard(filePath[0], c.getString(c.getColumnIndex(HtmlPage.COL_FILENAME)))));
						pm = getContext().getPackageManager();
						List<ResolveInfo> activitiesPDF = pm.queryIntentActivities(intentToPDF, 0);
						if (activitiesPDF.size() > 0) {
							getContext().startActivity(intentToPDF);
						} else {
							// Do something else here. Maybe pop up a Dialog or
							// Toast
							Toast.makeText(getContext(), R.string.no_valid_pdf, Toast.LENGTH_LONG).show();
						}
						c.close();
					}

					break;
				case SDCardHelper.TYPE_PNG:
				case SDCardHelper.TYPE_GIF:
				case SDCardHelper.TYPE_JPG:
					Log.i(TAG, "view picture");
					c = mHtmlDbHelper.getPageInfo(url);
					if (c != null) {

						File picFile = mSdCardHelper.getFileFromSDCard(filePath[0],
								c.getString(c.getColumnIndex(HtmlPage.COL_FILENAME)));
						Intent intentToPic = new Intent(Intent.ACTION_VIEW);
						intentToPic.setDataAndType(Uri.parse("file://" + Uri.fromFile(picFile).getPath()), "image/*");
						pm = getContext().getPackageManager();
						List<ResolveInfo> activitiesPic = pm.queryIntentActivities(intentToPic, 0);
						if (activitiesPic.size() > 0) {
							getContext().startActivity(intentToPic);
						} else {
							// Do something else here. Maybe pop up a Dialog or
							// Toast
							Toast.makeText(getContext(), R.string.no_valid_pictures, Toast.LENGTH_LONG).show();
						}
						c.close();
					}

					break;
				case SDCardHelper.TYPE_MP3:
					Log.i(TAG, "play audio");

					c = mHtmlDbHelper.getPageInfo(url);
					if (c != null) {

						File mp3File = mSdCardHelper.getFileFromSDCard(filePath[0],
								c.getString(c.getColumnIndex(HtmlPage.COL_FILENAME)));
						Intent intentToMp3 = new Intent(Intent.ACTION_VIEW);
						intentToMp3.setDataAndType(Uri.parse("file://" + Uri.fromFile(mp3File).getPath()), "audio/mp3");
						pm = getContext().getPackageManager();
						List<ResolveInfo> activitiesAudio = pm.queryIntentActivities(intentToMp3, 0);
						if (activitiesAudio.size() > 0) {
							getContext().startActivity(intentToMp3);
						} else {
							// Do something else here. Maybe pop up a Dialog or
							// Toast
							Toast.makeText(getContext(), R.string.no_valid_audio, Toast.LENGTH_LONG).show();
						}
						c.close();
					}

					break;
				case SDCardHelper.TYPE_MP4:
				case SDCardHelper.TYPE_RMVB:
				case SDCardHelper.TYPE_FLV:
					Log.i(TAG, "play video");

					c = mHtmlDbHelper.getPageInfo(url);
					if (c != null) {

						File videoFile = mSdCardHelper.getFileFromSDCard(filePath[0],
								c.getString(c.getColumnIndex(HtmlPage.COL_FILENAME)));
						Intent intentToVideo = new Intent(Intent.ACTION_VIEW);
						intentToVideo.setDataAndType(Uri.parse("file://" + Uri.fromFile(videoFile).getPath()),
								"video/flv");
						pm = getContext().getPackageManager();
						List<ResolveInfo> activitiesVideo = pm.queryIntentActivities(intentToVideo, 0);
						if (activitiesVideo.size() > 0) {
							getContext().startActivity(intentToVideo);
						} else {
							// Do something else here. Maybe pop up a Dialog or
							// Toast
							Toast.makeText(getContext(), R.string.no_valid_video, Toast.LENGTH_LONG).show();
						}
						c.close();
					}
					break;

				}
			}
		}
	}

}
